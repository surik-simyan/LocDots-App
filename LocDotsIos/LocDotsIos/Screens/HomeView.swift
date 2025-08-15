//
//  HomeView.swift
//  LocDotsIos
//
//  Created by Surik Simonyan on 05.06.25.
//  Copyright © 2025 orgName. All rights reserved.
//
import SwiftUI
import LocDotsShared

struct HomeView: View {
    let onNavigateToMessageScreen: () -> Void
    @StateObject private var viewModel = HomeViewModel()
    @State private var showBottomSheet = false
    @State private var showingErrorAlert = false
    @State private var errorMessage: String = ""

    var body: some View {
        NavigationView {
            ZStack {
                Color(hex: ColorsKt.GrayHex)

                VStack {
                    switch viewModel.dots {
                    case .idle:
                        Spacer()
                    case .loading:
                        ProgressView("Loading...")
                            .progressViewStyle(CircularProgressViewStyle(tint: Color(hex: ColorsKt.PlatinumHex)))
                            .scaleEffect(1.5)
                            .foregroundColor(Color(hex: ColorsKt.PlatinumHex))
                        Spacer()
                    case .error(let message):
                        Spacer()
                        Text(message)
                            .foregroundColor(Color(hex: ColorsKt.PlatinumHex))
                            .padding()
                            .background(Color(hex: ColorsKt.EerieBlackHex).opacity(0.8))
                            .cornerRadius(10)
                            .onAppear {
                                errorMessage = message
                                showingErrorAlert = true
                            }
                        Spacer()
                    case .success(let items):
                        if items.isEmpty {
                            EmptyStateContent(onCreateDot: onNavigateToMessageScreen)
                        } else {
                            List {
                                ForEach(items) { item in
                                    MessageCard(dot: item)
                                        .listRowBackground(Color(hex: ColorsKt.GrayHex))
                                }
                            }
                            .listStyle(.plain)
                            .refreshable {
                                viewModel.getItems()
                            }
                        }
                    }
                }
                .alert("Error", isPresented: $showingErrorAlert) {
                    Button("OK") { showingErrorAlert = false }
                } message: {
                    Text(errorMessage)
                }
            }
            .navigationTitle("Locdots") // You can set a title here
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .navigationBarLeading) {
                    HStack {
                        Button(action: { showBottomSheet = true }) {
                            Image(systemName: "arrow.up.arrow.down.circle.fill")
                                .font(.title2)
                                .foregroundColor(Color(hex: ColorsKt.PlatinumHex))
                        }
                        Button(action: { viewModel.getItems() }) {
                            Image(systemName: "arrow.clockwise.circle.fill")
                                .font(.title2)
                                .foregroundColor(Color(hex: ColorsKt.PlatinumHex))
                        }
                    }
                }
                ToolbarItem(placement: .navigationBarTrailing) {
                    Button(action: onNavigateToMessageScreen) {
                        Image(systemName: "pencil.circle.fill")
                            .font(.largeTitle)
                            .foregroundColor(Color(hex: ColorsKt.EerieBlackHex))
                            .background(Circle().fill(Color(hex: ColorsKt.PlatinumHex)).frame(width: 40, height: 40)) // Adjusted size for toolbar
                    }
                }
            }
        }
        .sheet(isPresented: $showBottomSheet) {
            BottomSheetContent(selectedSortType: viewModel.sortType) { newSortType in
                viewModel.sortType = newSortType
                showBottomSheet = false
            }
            .presentationDetents([.medium, .large])
            .background(Color(hex: ColorsKt.EerieBlackHex))
        }
        .onAppear {
            viewModel.getItems()
        }
    }
}
