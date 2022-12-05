# McWonderland 官方機器人

### 使用語言和相關套件
  - Kotlin
  - JDA
  - MongoDB
  - Gradle
  - [java-mojang-api](https://github.com/SparklingComet/java-mojang-api)
  - Guice

### 開發技術與模式
本專案全程使用 TDD 作為開發方式，確保程式持續在測試保護下重構，另外使用 DI 的設計模式，確保程式的可測試性。

專案中可以看到被分為 domain, access, discord, minecraft 四個 package，**核心 domain 層的東西相對獨立，主要撰寫的是程式的邏輯**。其他的 package 則會實做 domain 層的 Interface，透過依賴反轉的方式，確保核心始終不依賴於外部的實作。

最後在組合根，我們使用 Guice 的幫助來完成依賴注入，這是程式中唯一會使用 DI Container 的地方。

關於上述的技術，有興趣的朋友們可以參考以下書籍，這個專案也是受到這些書籍的啟發而寫出來的。

  * [依賴注入：原理、實作與設計模式](https://www.tenlong.com.tw/products/9789864344987)
  * [Kent Beck 的測試驅動開發：案例導向的逐步解決之道](https://www.tenlong.com.tw/products/9789864345618?list_name=srh)