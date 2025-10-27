
//
//  HomeViewModel.swift
//  LocDotsIos
//
//  Created by Surik Simonyan on 18.06.25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import Combine
import LocDotsShared

@MainActor
class HomeViewModel: ObservableObject {
    enum HomeScreenState: Equatable {
        case idle
        case loading
        case error(String)
        case success([Dot])
    }

    @Published var dots: HomeScreenState = .idle
    @Published var sortType: DotSort = .postDate
    
    private var cancellables = Set<AnyCancellable>()
    private var fetchTask: Task<Void, Never>? = nil
    
    private var getAllDotsUseCase: GetAllDotsUseCase {
        UseCaseProvider.shared.getAllDotsUseCase
    }

    init() {
        getItems()
    }

    func getItems() {
        fetchTask?.cancel()
        dots = .loading
        
        fetchTask = Task {
            do {
                let result = try await getAllDotsUseCase.invoke(sortingType: sortType)
                
                result
                    .onSuccess { data in
                        if let dots = data as? [Dot] {
                            self.dots = .success(dots)
                        }
                    }
                    .onFailure { error in
                        self.dots = .error(error.message)
                    }
            } catch is CancellationError {
                self.dots = .idle
            } catch {
                self.dots = .error(error.localizedDescription)
            }
        }
    }
    
    deinit {
        fetchTask?.cancel()
    }
}
