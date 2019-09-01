# sheetmenu
Library for speedy implementation menu with BottomSheet

[![Release](https://jitpack.io/v/whalemare/sheetmenu.svg)](https://jitpack.io/#whalemare/sheetmenu)

![Screenshot](screens/1.3.2.gif)

Usage
-----

Use it in Kotlin

```kotlin
SheetMenu("Post", listOf("Send mail", "Send telegram", "Receive parcel")).show(this)
```

Apply not required params for more customization
```kotlin
SheetMenu(
    title = "Post",
    menu = R.menu.my_custom_menu, // you can just pass menu resource if you need static items
//  actions = listOf(ActionItem(id = 0, title = "Send mail", image = getDrawableIcon())), // or create ActionItem when you need dynamic titles of icons
//  actions = listOf("Send mail", "Send telegram", "Receive parcel"), // also, you can simplify it by passing strings for showing only text of items
    layoutProvider = LinearLayoutProvider(), // linear layout enabled by default
//  layoutProvider = GridLayoutProvider() // but if you need grid, you can do it
//  layoutProvider = object: LayoutProvider { ... } // also, you can define your own layout
    onClick = { item -> Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show() }, // handle clicks on item
    onCancel = { Toast.makeText(this, "Closed", Toast.LENGTH_SHORT).show() } // handle close event
).show(this)
```

Install
-------

Be sure, that you have `Jitpack` in your root gradle file

```diff
allprojects {
    repositories {
      jcenter()
+     maven { url "https://jitpack.io" }
    }
}
```

Include dependency with `SheetMenu` in your app.gradle file with:

```diff
implementation 'com.github.whalemare:sheetmenu:2.0.1'
```

Roadmap
-------
[] Customization layout of ActionItem

License
-------

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
