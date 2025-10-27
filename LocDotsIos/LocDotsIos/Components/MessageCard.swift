//
//  MessageCard.swift
//  LocDotsIos
//
//  Created by Surik Simonyan on 05.06.25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import LocDotsShared

struct MessageCard: View {
    let dot: Dot
    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            Text(dot.message)
                .padding(16)
                .foregroundColor(Color(UIColorKt.Platinum))
                .multilineTextAlignment(.leading)
                .frame(maxWidth: .infinity, alignment: .leading)
            
            Divider()
                .frame(height: 1)
                .overlay(Color(UIColorKt.Jet))
            
            HStack {
                Text(dot.formattedDate)
                    .foregroundColor(Color(UIColorKt.Platinum))
                    .multilineTextAlignment(.leading)
                    .frame(maxWidth: .infinity, alignment: .leading)
                
                Spacer()
                
                Text(dot.formattedDistance)
                    .foregroundColor(Color(UIColorKt.Platinum))
                    .multilineTextAlignment(.trailing)
                    .frame(maxWidth: .infinity, alignment: .trailing)
            }
            .padding(16)
            
        }
        .frame(minHeight: 100, alignment: .top)
        .background(
            RoundedRectangle(cornerRadius: 8)
                .fill(Color(UIColorKt.DavyGray))
        )
    }
}
