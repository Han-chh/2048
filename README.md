# 2048

A compact Java Swing implementation of the 2048 puzzle, focused on grid movement, tile merging, scoring, and restartable play.

[中文说明](README_zh.md)

## Overview

This project turns the familiar 2048 rules into a small desktop application. Players move a 4×4 board with the arrow keys or WASD, combine equal tiles, and build a higher score while the game continuously adds new tiles and checks for terminal states.

## Screenshot

![A mid-game 2048 board showing merged numbered tiles](assets/screenshots/2048-gameplay.png)

The screenshot is captured from the running Swing game and shows a real mid-game board.

## Features

- Arrow-key and WASD controls
- Standard equal-tile merge behavior
- Live score updates
- New Game reset
- Game-over detection

## How it works

The Swing UI maintains a 4×4 tile model. Each move compacts the selected rows or columns, merges valid neighbors once, inserts a new tile, and refreshes the rendered board.

## Run

The committed JAR was verified with Java 25:

```bash
java -jar 2048.jar
```

## Current limitations

- No automated test suite is included.
- The current JAR targets Java 25; rebuild from source before claiming compatibility with older Java releases.
