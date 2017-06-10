# sheetmenu
Library for speedy implementation menu with BottomSheet

[![Release](https://jitpack.io/v/whalemare/sheetmenu.svg)](https://jitpack.io/#whalemare/sheetmenu)

![Screenshot](v1.1.gif)

Usage
-----

Use it in Kotlin with `apply` extension

```kotlin
        SheetMenu().apply {
            titleId = R.string.title
            click = MenuItem.OnMenuItemClickListener { true }
            menu = R.menu.menu
            autoCancel = false
        }.show(this)
```

or in Java with `Builder` pattern 

```java
        SheetMenu.with(this)
                .setTitle(R.string.title)
                .setMenu(R.menu.menu)
                .setAutoCancel(false)
                .setClick(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return false;
                    }
                }).show();
```

Install
-------

Be sure, that you have `Jitpack` in your root gradle file

```
allprojects {
    repositories {
      jcenter()
      maven { url "https://jitpack.io" }
    }
}
```

Include dependency with `BottomSheet` in your app.gradle file with:

```groovy
compile 'com.github.whalemare:sheetmenu:1.2'
```


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
