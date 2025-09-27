import SwiftUI
import LocDotsShared

@main
struct iOSApp: App {
    
    init() {
        UISegmentedControl.appearance().backgroundColor = UIColorKt.Gray
        HelperKt.doInitKoin()
    }
    
	var body: some Scene {
		WindowGroup {
            HomeView()
                .ignoresSafeArea(.all)
		}
	}
}
