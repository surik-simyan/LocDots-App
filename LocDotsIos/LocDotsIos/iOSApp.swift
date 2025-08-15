import SwiftUI
import LocDotsShared

@main
struct iOSApp: App {
    
    init() {
        UISegmentedControl.appearance().backgroundColor = UIColor(Color(hex: ColorsKt.GrayHex))
    }
    
	var body: some Scene {
		WindowGroup {
            HomeView()
		}
	}
}
