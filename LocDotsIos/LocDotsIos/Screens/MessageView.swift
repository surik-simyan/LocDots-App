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
    @State private var text: String = "This is some sample text entered by the user..."
    private let maxChar = 500

    var body: some View {
        NavigationStack {
            ZStack {
                Color(hex: ColorsKt.GrayHex)
                    .ignoresSafeArea()

                VStack {
                    TextEditor(text: $text)
                        .scrollContentBackground(.hidden)
                        .background(Color(hex: ColorsKt.GrayHex))
                        .foregroundColor(Color(hex: ColorsKt.PlatinumHex))
                        .font(.system(size: 16))
                        .frame(minHeight: 0, maxHeight: 300)
                        .cornerRadius(0)
                        .onChange(of: text) { _ in
                            if text.count > maxChar {
                                self.text = String(text.prefix(maxChar))
                            }
                        }

                    Spacer()
                }
            }
            .navigationBarTitleDisplayMode(.inline)
            .toolbarBackground(Color(hex: ColorsKt.EerieBlackHex), for: .navigationBar)
            .toolbarBackground(.visible, for: .navigationBar)
            .toolbar {
                ToolbarItem(placement: .principal) {
                    Text("\(text.count) / \(maxChar)")
                        .foregroundColor(Color(hex: ColorsKt.PlatinumHex))
                        .frame(maxWidth: .infinity, alignment: .trailing)
                        .padding(.trailing, 5)
                }

                ToolbarItem(placement: .navigationBarTrailing) {
                    Button(action: {
                        // Send message logic here
                    }) {
                        Image(systemName: "paperplane.fill")
                            .foregroundColor(Color(hex: ColorsKt.PlatinumHex))
                    }
                }
            }
        }
        .tint(Color(hex: ColorsKt.EerieBlackHex))
    }
}

#Preview {
    MessageView()
}
