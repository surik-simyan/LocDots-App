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
    let sortOptions: [DotSort] = [.postDistance, .postDate]
    
    @State private var temporarySelectedSortType: DotSort

    init(selectedSortType: Binding<DotSort>, onApply: @escaping () -> Void) {
        self._selectedSortType = selectedSortType
        self.onApply = onApply
        self._temporarySelectedSortType = State(initialValue: selectedSortType.wrappedValue)
    }
    
    var body: some View {
        VStack(spacing: 16) {
            Text("Sort by")
                .font(.system(size: 24, weight: .bold))
                .foregroundColor(Color(UIColorKt.Platinum))
                .frame(maxWidth: .infinity, alignment: .center)
                .padding(.vertical, 8)
            
            Picker("Sort Options", selection: $temporarySelectedSortType) {
                ForEach(sortOptions, id: \.self) { option in
                    Text(option.value)
                        .tag(option)
                }
            }
            .pickerStyle(.segmented)
            .tint(Color(UIColorKt.Platinum))
            
        
            Button(action: {
                selectedSortType = temporarySelectedSortType
                onApply()
            }) {
                Text("Apply")
                    .fontWeight(.bold)
                    .frame(maxWidth: .infinity)
                    .padding(.vertical, 12)
                    .background(Color(UIColorKt.Platinum))
                    .foregroundColor(Color(UIColorKt.EerieBlack))
                    .cornerRadius(8)
            }
        }
        .padding(16)
        .background(Color(UIColorKt.EerieBlack))
    }
}
