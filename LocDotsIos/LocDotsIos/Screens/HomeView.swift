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
    @StateObject private var viewModel = HomeViewModel()
    @State private var showBottomSheet = false
    @State private var showingErrorAlert = false
    @State private var errorMessage: String = ""
    
    var body: some View {
        NavigationView {
            ZStack {
                Color(UIColorKt.Gray)
                    .ignoresSafeArea()
                
                VStack(spacing: 0) {
                    VStack {
                        switch viewModel.dots {
                        case .idle:
                            Spacer()
                            Text("Idle state. Pull to refresh or select sort options.")
                                .foregroundColor(Color(UIColorKt.Platinum))
                            Spacer()
                        case .loading:
                            Spacer()
                            ProgressView("Loading...")
                                .progressViewStyle(CircularProgressViewStyle(tint: Color(UIColorKt.Platinum)))
                                .scaleEffect(1.5)
                                .foregroundColor(Color(UIColorKt.Platinum))
                            Spacer()
                        case .error(let message):
                            Spacer()
                            Text(message)
                                .foregroundColor(Color(UIColorKt.Platinum))
                                .padding()
                                .background(Color(UIColorKt.EerieBlack).opacity(0.8))
                                .cornerRadius(10)
                                .onAppear {
                                    errorMessage = message
                                    showingErrorAlert = true
                                }
                            Spacer()
                        case .success(let items):
                            if items.isEmpty {
                                ScrollView {
                                    VStack(spacing: 16) {
                                        Spacer()
                                        Text("No dots nearby, be the first one")
                                            .foregroundColor(Color(UIColorKt.Platinum))
                                        
                                        NavigationLink(destination: MessageView()) {
                                            Text("Create dot")
                                                .foregroundColor(Color(UIColorKt.EerieBlack))
                                                .padding(.horizontal, 20)
                                                .padding(.vertical, 10)
                                                .background(Color(UIColorKt.Platinum))
                                                .cornerRadius(8)
                                        }
                                        .padding(.horizontal, 16)
                                        Spacer()
                                    }
                                    .frame(maxWidth: .infinity, minHeight: UIScreen.main.bounds.height * 0.8)
                                }
                                .refreshable {
                                    viewModel.getItems()
                                }
                            } else {
                                List {
                                    ForEach(items) { item in
                                        MessageCard(dot: item)
                                            .listRowBackground(Color(UIColorKt.Gray))
                                            .listRowSeparator(.hidden) // Added to hide dividers
                                    }
                                }
                                .listStyle(.plain)
                                .refreshable {
                                    viewModel.getItems()
                                }
                            }
                        }
                    }
                    .frame(maxWidth: .infinity, maxHeight: .infinity) // Content takes available space
                    .alert("Error", isPresented: $showingErrorAlert) {
                        Button("OK") { showingErrorAlert = false }
                    } message: {
                        Text(errorMessage)
                    }
                    
                    // Custom Bottom Bar
                    HStack {
                        Button(action: { showBottomSheet = true }) {
                            Image(systemName: "arrow.up.arrow.down")
                                .foregroundColor(Color(UIColorKt.Platinum))
                                .imageScale(.large)
                        }
                        
                        Spacer()
                        
                        NavigationLink(destination: MessageView()) {
                            Image(systemName: "pencil")
                                .foregroundColor(Color(UIColorKt.Platinum))
                                .imageScale(.large)
                        }
                    }
                    .padding()
                    .frame(maxWidth: .infinity)
                    .background(Color(UIColorKt.EerieBlack))
                }
            }
            .navigationBarTitleDisplayMode(.inline)
        }
        .sheet(isPresented: $showBottomSheet) {
            ZStack {
                Color(UIColorKt.EerieBlack)
                    .ignoresSafeArea()
                
                BottomSheetContent(selectedSortType: $viewModel.sortType) {
                    viewModel.getItems()
                    showBottomSheet = false
                }
            }
            .presentationDetents([.fraction(0.25)])
            .presentationDragIndicator(.visible)
            .preferredColorScheme(.dark)
        }
    }
}
