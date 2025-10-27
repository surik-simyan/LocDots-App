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

class MessageViewModel: ObservableObject {
    enum MessageScreenState: Equatable {
        case idle
        case loading
        case error(String)
        case success
    }

    @Published var dot: MessageScreenState = .idle
    
    private var cancellables = Set<AnyCancellable>()
    private var uploadTask: Task<Void, Never>? = nil
    
    private var createDotUseCase: CreateDotUseCase {
        return UseCaseProvider.shared.createDotUseCase
    }
    
    func onSendClick(message: String) {
        dot = .loading
        uploadTask?.cancel()
        
        uploadTask = Task {
            do {
                let result = try await createDotUseCase.invoke(message: message)
                
                result
                    .onSuccess { data in
                        self.dot = .success
                    }
                    .onFailure { error in
                        self.dot = .error(error.message)
                    }
            } catch is CancellationError {
                self.dot = .idle
            } catch {
                self.dot = .error(error.localizedDescription)
            }
        }
    }
    
    deinit {
        uploadTask?.cancel()
    }
}
