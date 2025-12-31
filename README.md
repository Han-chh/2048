# 2048 Game Project

## Project Description

This is a classic 2048 number puzzle game implemented in Java Swing. Players move tiles using keyboard arrow keys or WASD keys, merging tiles with the same numbers to reach 2048 or higher scores.

## Features

### Core Features
- **4x4 Game Grid**: 16 tile positions supporting movement and merging of number tiles
- **Tile Movement**: Supports movement in four directions (up, down, left, right)
- **Merge Mechanism**: Adjacent tiles with the same number automatically merge, doubling their value
- **Scoring System**: Score increases with each merge operation
- **Random Generation**: New 2 or 4 tiles randomly appear in empty positions after each move
- **Game Over Detection**: Game ends when no valid moves are possible

### User Interface
- **Graphical Interface**: Beautiful interface built with Swing components
- **Background Images**: Includes game background and tile number images
- **Score Display**: Real-time display of current score
- **New Game Button**: Start a new game at any time
- **Control Instructions**: Shows instructions for using arrow keys to move tiles

### Controls
- **Arrow Keys**: ↑↓←→ to move tiles
- **WASD Keys**: W(Up) S(Down) A(Left) D(Right) to move tiles
- **Mouse**: Click "New Game" button to start a new game

## Technical Implementation

### Architecture
- **MainGame.java**: Program entry point, launches the game interface
- **GameUI.java**: Main UI class, handles user input, game logic, and display
- **Tile.java**: Tile class, manages individual tile state and display

### Key Technologies
- **Swing GUI Framework**: Builds the graphical user interface
- **Event-Driven**: Keyboard event listeners handle user input
- **Multithreading**: Uses threads to implement movement animation effects
- **Resource Management**: Loads image resources from classpath

## Potential Bugs

### Known Issues
1. **Complex Movement Logic**: The move method implementation is complex and may cause abnormal movement at boundaries
2. **Incomplete Merge Detection**: The added array prevents duplicate merges but may fail in certain scenarios
3. **Delayed Game Over Detection**: Game over is detected after new tiles are generated, potentially allowing invalid moves
4. **Image Resource Dependency**: Missing image files in the Images folder will cause program crashes
5. **Fixed Window Size**: May appear too small on high-resolution screens
6. **Keyboard Focus Issues**: Cannot receive keyboard input when window loses focus

### Potential Risks
- **Memory Leaks**: Long-running sessions may accumulate unused objects
- **Thread Safety**: Race conditions may exist between UI updates and game logic threads
- **Insufficient Exception Handling**: Lacks comprehensive exception catching and handling

## Improvement Suggestions

### Feature Enhancements
1. **Undo Feature**: Add undo functionality to allow players to revert recent moves
2. **Save/Load Game**: Support game state persistence
3. **High Score Records**: Record and display historical high scores
4. **Animation Effects**: Add smooth animations for tile movement and merging
5. **Sound Effects**: Add background music and sound effects
6. **Difficulty Levels**: Support different grid sizes (5x5, 6x6, etc.)

### User Experience
1. **Responsive Design**: Adapt to different screen resolutions
2. **Touch Support**: Support swipe gestures for touch screen devices
3. **Theme Switching**: Provide multiple visual theme options
4. **Detailed Instructions**: More comprehensive game rule explanations
5. **Statistics**: Display game duration, move count, and other statistics

### Code Quality
1. **Refactor Code Structure**: Separate game logic from UI logic using MVC pattern
2. **Unit Tests**: Add comprehensive unit test coverage
3. **Error Handling**: Improve exception handling with user-friendly error messages
4. **Performance Optimization**: Optimize rendering performance, reduce unnecessary repaints
5. **Code Documentation**: Add detailed code comments and documentation

### Technical Upgrades
1. **Modern GUI Framework**: Consider migrating to JavaFX or other modern GUI frameworks
2. **Configuration Files**: Support customizing game parameters through config files
3. **Internationalization**: Support multi-language interfaces
4. **Network Features**: Add online leaderboards or multiplayer functionality

## Runtime Environment

- **Java Version**: JDK 8 or higher
- **Dependencies**: Uses only standard Java libraries, no additional dependencies
- **Operating Systems**: Supports Windows, macOS, Linux

## Build and Run

```bash
# Compile
javac -d out src/*.java

# Copy resources (required)
xcopy src\images out\images /E /I

# Run
java -cp out MainGame
```

## License

This project is licensed under the MIT License, see LICENSE file for details.
