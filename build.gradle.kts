// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.2" apply false

}
dependencies {
    implementation 'com.github.bumptech.glide:glide:4.12.0'
}
//dependencies {
//    //添加Glide 库的依赖
//    implementation("com.github.bumptech.glide:glide:4.12.0")
//    kapt("com.github.bumptech.glide:compiler:4.12.0")
//}