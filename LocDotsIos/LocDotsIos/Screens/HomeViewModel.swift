//
//  HomeViewModel.swift
//  LocDotsIos
//
//  Created by Surik Simonyan on 18.06.25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import LocDotsShared

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

    init() {
        $sortType
            .sink { [weak self] _ in
                self?.getItems()
            }
            .store(in: &cancellables)
        getItems()
    }

    func getItems() {
        dots = .loading
        DispatchQueue.main.asyncAfter(deadline: .now() + 1.5) { [weak self] in
            guard let self = self else { return }
            let sampleDots: [Dot] = [
                Dot(title: "First Dot", content: "This is the content of the first dot."),
                Dot(title: "Second Dot", content: "Another dot with some interesting content."),
                Dot(title: "Third Dot", content: "The last dot for demonstration purposes.")
            ]
            let sortedDots = sampleDots.sorted { d1, d2 in
                switch self.sortType {
                case .postDate:
                    return d1.title < d2.title
                case .name:
                    return d1.title < d2.title
                }
            }
            self.dots = .success(sortedDots)
        }
    }
}
