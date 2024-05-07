# Tucil3_13522114
Small Assignment 3 IF2211 Algorithm Strategy Semester II 2023/2024 Solving Word Ladder Game Using UCS Algorithm, Greedy Best First Search, and A*

## Table of Contents

- [General Info](#general-information)
- [Technologies Used](#technologies-used)
- [Requirements](#requirements)
- [Features](#features)
- [Setup](#setup)
- [Usage](#usage)
- [Author](#author)

## General Information

The Word Ladder game is a word puzzle where the objective is to transform one word into another, changing only one letter at a time, while ensuring that every intermediate step is also a valid word. The challenge lies in finding the shortest sequence of valid words to connect the starting word to the target word.

How to play?

1. Start and End Words: You begin with two words – a starting word and an end word. The goal is to transform the starting word into the end word.
2. Word List: You have a list of valid words.
3. Rules: The transformation between words can only involve changing one letter at a time, and at each step, the resulting word must also be a valid word from the word list.
4. Objective: The objective is to find the shortest sequence of transformations (or ladder) from the starting word to the end word.
5. Example: Let's say the starting word is "CAT" and the end word is "DOG". A valid ladder might be: CAT -> COT -> DOT -> DOG. At each step, only one letter changes, and the resulting word is in the word list.

## Technologies Used

- Java version "22.0.1" 2024-04-16
- Java(TM) SE Runtime Environment (build 22.0.1+8-16)

## Features

- Uniform Cost Search (UCS) Algorithm
- Greedy Best First Search (GBFS) Algorithm
- A\* Search Algorithm

## Requirements

- Java version "22.0.1" 2024-04-16
- Java(TM) SE Runtime Environment (build 22.0.1+8-16)
- Windows - Linux

## Setup

- To run using makefile, simply run

```bash
make all
```

The above command simply run `make clean`, `make build`, and finally `make run` sequentially.

- To run manually, build the program by running

```bash
javac -d bin src/*.java
```

Run the program by running

```bash
java -cp bin App
```

Clean the bin by running

```bash
rm -rf bin/*
```

## Usage

1. Input start word
2. Input end word
3. Input algorithm (UCS / GBFS / A\*)
4. Wait for result
5. Enjoy ☕

# Reference

The dictionary utilized for testing course compatibility in this program is sourced from the following  [repository](https://docs.oracle.com/javase/tutorial/collections/interfaces/examples/dictionary.txt).

# Author
| No. | Name                           | NIM |
|-----|--------------------------------|------------|
| 1.  | Muhammad Dava Fathurrahman      | 13522114   |
