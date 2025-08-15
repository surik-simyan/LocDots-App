//
//  BottomSheetContent.swift
//  LocDotsIos
//
//  Created by Surik Simonyan on 09.06.25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import LocDotsShared

struct BottomSheetContent: View {
    @Binding var selectedSortType: DotSort
    var onApply: () -> Void
    let sortOptions: [DotSort] = [DotSort.postDistance, DotSort.postDate]
    
    var body: some View {
        VStack(spacing: 16) {
            Text("Sort by")
                .font(.system(size: 24, weight: .bold))
                .foregroundColor(Color(hex: ColorsKt.PlatinumHex))
                .frame(maxWidth: .infinity, alignment: .center)
                .padding(.vertical, 8)
            
            Picker("Sort Options", selection: $selectedSortType) {
                ForEach(sortOptions, id: \.self) { option in
                    Text(option.value)
                        .tag(option)
                }
            }
            .pickerStyle(.segmented)
            .tint(Color(hex: ColorsKt.PlatinumHex))
            
        
            Button(action: {
                onApply()
            }) {
                Text("Apply")
                    .fontWeight(.bold)
                    .frame(maxWidth: .infinity)
                    .padding(.vertical, 12)
                    .background(Color(hex: ColorsKt.PlatinumHex))
                    .foregroundColor(Color(hex: ColorsKt.EerieBlackHex))
                    .cornerRadius(8)
            }
        }
        .padding(16)
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
        .background(Color(hex: ColorsKt.EerieBlackHex).ignoresSafeArea())
    }
}
