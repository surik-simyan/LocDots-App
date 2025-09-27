//
//  MessageView.swift
//  LocDotsIos
//
//  Created by Surik Simonyan on 09.06.25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import LocDotsShared

struct MessageView: View {
    @StateObject private var viewModel = MessageViewModel()
    @State private var showingErrorAlert = false
    @State private var showingSuccessAlert = false
    @State private var errorMessage: String = ""
    
    @State private var text: String = "This is some sample text entered by the user..."
    private let maxChar = 300
    
    @Environment(\.dismiss) private var dismiss

    var body: some View {
            ZStack {
                Color(UIColorKt.Gray)
                    .ignoresSafeArea()

                VStack {
                    TextEditor(text: $text)
                        .scrollContentBackground(.hidden)
                        .background(Color(UIColorKt.Gray))
                        .foregroundColor(Color(UIColorKt.Platinum))
                        .font(.system(size: 16))
                        .frame(minHeight: 0, maxHeight: 300)
                        .onChange(of: text) { _ in
                            if text.count > maxChar {
                                self.text = String(text.prefix(maxChar))
                            }
                        }

                    Spacer()
                    
                    if case .loading = viewModel.dot {
                        ProgressView("Loading...")
                            .progressViewStyle(CircularProgressViewStyle(tint: Color(UIColorKt.Platinum)))
                            .scaleEffect(1.5)
                            .foregroundColor(Color(UIColorKt.Platinum))
                    }

                    if case .error(let message) = viewModel.dot {
                        Text("").onAppear {
                            errorMessage = message
                            showingErrorAlert = true
                        }
                    } else if case .success = viewModel.dot {
                        Text("").onAppear {
                            showingSuccessAlert = true
                        }
                    }
                }
            }
            .navigationBarTitleDisplayMode(.inline)
            .navigationBarBackButtonHidden(true) // hide default blue back button
            .toolbarBackground(Color(UIColorKt.EerieBlack), for: .navigationBar)
            .toolbarBackground(.visible, for: .navigationBar)
            .toolbar {
                // Custom white back button
                ToolbarItem(placement: .navigationBarLeading) {
                    Button(action: { dismiss() }) {
                        Image(systemName: "chevron.left")
                            .foregroundColor(Color(UIColorKt.Platinum))
                    }
                }

                ToolbarItem(placement: .principal) {
                    Text("\(text.count) / \(maxChar)")
                        .foregroundColor(Color(UIColorKt.Platinum))
                        .frame(maxWidth: .infinity, alignment: .trailing)
                        .padding(.trailing, 5)
                }

                ToolbarItem(placement: .navigationBarTrailing) {
                    Button(action: {
                        viewModel.onSendClick(message: text)
                    }) {
                        Image(systemName: "paperplane.fill")
                            .foregroundColor(Color(UIColorKt.Platinum))
                    }
                }
            }
            .alert("Huh, something went wrong", isPresented: $showingErrorAlert) {
                Button("OK") { showingErrorAlert = false }
            } message: {
                Text(errorMessage)
            }
            .alert("Success", isPresented: $showingSuccessAlert) {
                Button("OK") {
                    showingSuccessAlert = false
                    dismiss()
                }
            } message: {
                Text("Dot successfully uploaded")
            }
        .tint(Color(UIColorKt.EerieBlack))
    }
}
