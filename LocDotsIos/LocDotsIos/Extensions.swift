//
//  Extensions.swift
//  LocDotsIos
//
//  Created by Surik Simonyan on 05.06.25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

extension Color {
    init(hex: Int64) {
        let r = Double((hex >> 16) & 0xFF) / 255
        let g = Double((hex >> 8) & 0xFF) / 255
        let b = Double(hex & 0xFF) / 255
        self.init(red: r, green: g, blue: b)
    }
}
