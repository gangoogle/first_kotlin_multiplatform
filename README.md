## 关于
这是一个面向 Android、iOS 和桌面平台的 Kotlin Multiplatform 项目。
/composeApp 用于存放将在你的 Compose Multiplatform 应用中共享的代码。它包含了多个子文件夹：
commonMain：用于所有平台通用的代码。
其他文件夹对应的是仅会为特定平台编译的 Kotlin 代码。例如，如果你希望在 Kotlin 应用的 iOS 部分使用 Apple 的 CoreCrypto，那么 iosMain 就是放置这类调用的合适位置。
/iosApp 包含的是 iOS 应用代码。即使你使用 Compose Multiplatform 共享了 UI，也仍然需要这个目录作为 iOS 应用的入口点。你也应该在这个目录中添加项目所需的 SwiftUI 代码。

## 模块
voyage路由导航集成
compose viewmodel 数据流封装: state、effect、
### 网络
ktor网络框架集成
kotlinx.serialization序列化工具集成
请求脱壳+异常处理+请求API封装
全局进度弹窗