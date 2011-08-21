[<img src="/o2genum/CoreGame/raw/master/res/drawable-ldpi/icon.png" width="20px"> CoreGame](https://market.android.com/details?id=ru.o2genum.coregame): a simple game for Android
===================================

Screenshots:
------------

<img src="/o2genum/CoreGame/raw/master/etc/screenshot-for-readme1.png" width="32.5%"> &nbsp;
<img src="/o2genum/CoreGame/raw/master/etc/screenshot-for-readme2.png" width="32.5%"> &nbsp;
<img src="/o2genum/CoreGame/raw/master/etc/screenshot-for-readme3.png" width="32.5%"> 

The game:
---------

The rules are simple:

* protect the core (big cyan dot in the center of the screen) as long as possible
* gain health (cyan dots) and use shields (blue dots)

This game is an Android version of [Hakim El Hattab's "Core"](http://www.chromeexperiments.com/detail/core/). It is available in [Android Market](https://market.android.com/details?id=ru.o2genum.coregame). If you want to install the game on your device, I recommend to search `pname:ru.o2genum.coregame` in the Market.

The code:
--------
Game framework (`ru.o2genum.coregame.framework` package) was initially taken from [Mario Zechner's "Beginning Android Games"](http://code.google.com/p/beginning-android-games/) book. I've modified a few sources and adapted them to my needs. The game itself is contained within the `ru.o2genum.coregame.game` package. Almost all game logic is in the `World.java`.

What I need:
------------
I need `.ogg` (preferred) or `.mp3` sounds for colliding dots. I may use one sound for all situations, two sounds (core collision / core shield collision) or three ones (health / shield / enemy dot collision). What about awesome background music ... I only dream of it.

License:
-------
The game is licensed under [Open Source Initiative MIT License](http://www.opensource.org/licenses/mit-license.php).
