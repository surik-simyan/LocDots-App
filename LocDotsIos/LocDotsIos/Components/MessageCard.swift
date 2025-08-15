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
                .foregroundColor(Color(hex: LocDotsShared.ColorsKt.PlatinumHex))
                .multilineTextAlignment(.leading)
                .frame(maxWidth: .infinity, alignment: .leading)
            
            Divider()
                .frame(height: 1)
                .overlay(Color(hex: ColorsKt.JetHex))
            
            HStack {
                Text(dot.date)
                    .foregroundColor(Color(hex: ColorsKt.PlatinumHex))
                    .multilineTextAlignment(.leading)
                    .frame(maxWidth: .infinity, alignment: .leading)
                
                Spacer()
                
                Text("16 km")
                    .foregroundColor(Color(hex: ColorsKt.PlatinumHex))
                    .multilineTextAlignment(.trailing)
                    .frame(maxWidth: .infinity, alignment: .trailing)
            }
            .padding(16)
            
        }
        .frame(minHeight: 100, alignment: .top)
        .background(
            RoundedRectangle(cornerRadius: 8)
                .fill(Color(hex: ColorsKt.DavyGrayHex))
        )
    }
}

//#Preview {
//    MessageCard(dot: <#T##LocDotsShared.Dot#>)
//}
