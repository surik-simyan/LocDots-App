//
//  EmptyStateContent.swift
//  LocDotsIos
//
//  Created by Surik Simonyan on 18.06.25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import LocDotsShared

struct EmptyStateContent: View {
    let onCreateDot: () -> Void
    var body: some View {
        VStack {
            Text("No dots nearby, be the first one")
                .foregroundColor(Color(UIColorKt.Platinum))
                .font(.title2)
                .padding()
            Spacer()
                .frame(height: 5)
            Button("Create dot", action: onCreateDot)
                .buttonStyle(.borderedProminent)
                .tint(Color(UIColorKt.Platinum))
                .foregroundColor(Color(UIColorKt.EerieBlack))
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(Color(UIColorKt.Gray))
    }
}
