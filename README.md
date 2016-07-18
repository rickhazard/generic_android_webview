# generic_android_webview
AndroidStudio project that wraps a webview. Easily change the URL in MainActivity.java to whatever you like. This is based on the excellent article on mobile app development using webviews by Gregg Milligan. I highly recommend checking it out (there's a section for iOS too): http://www.human-element.com/easy-android-app-development-webview/

Best way to use this is to start with an AndroidStudio project with an empty activity. You'll need to install java and AndroidStudio, of course, so if you haven't done that yet go ahead and do it now. I'll wait.

All set? Great. Now fire it up and create a project. Give it a cool name and company domain, select target devices, select Empty Activity (the default), and then [Finish].

The files you need to modify are:
 * activity_main.xml
 * MainActivity.java
 * strings.xml
 * AndroidManifest.xml

Don't worry about webviewClient.java, we won't need that for our purposes. There's a lot of other stuff in this repository that AndroidStudio creates for you. I was just lazy and did the ol' "git add *".

Oh yeah, in case you're running AndroidStudio on an AMD-based machine, the emulators won't work. At least they didn't for me and I tried a bunch of the non-x86 ones. Create one using Genymotion instead (genymotion.com, free for personal use). You can create a bunch of emulators then start one up. AndroidStudio will see it when you run your project. Sweet.
