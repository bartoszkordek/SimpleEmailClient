### [English version below](#EN-SimpleEmailClient)

# SimpleEmailClient

## Opis
Celem projektu jest implementacja protokołów SMTP, POP3 wraz z wykonaniem prostego interfejsu użytkownika. Projekt jest wykonywany w ramach przedmiotu Administracja Systemów Komputerowych, AGH, WIET.

## Uczestnicy
1. Bartosz Kordek (kordek@student.agh.edu.pl)
2. Marcin Włodarczyk (mwlodarc@student.agh.edu.pl)
3. Grzegorz Zacharski (gzacharski@student.agh.edu.pl)

## Technologie
* Język: Java 11
* Interfejs użytkownika: biblioteka Swing
* Konteneryzacja: Docker 
* Ciągła integracja (CI): GitHub Actions

## Jak uruchomić?
1. Wymagany jest zainstalowany [Docker](https://www.docker.com/).
1. Sklonuj lub pobierz projekt.
   * Do sklonowania projektu jest wymagany zainstalowany [Git](https://git-scm.com/).
   ```shell script
   git clone https://github.com/bartoszkordek/SimpleEmailClient.git
   ```
1. W głównym folderze projektu wpisz w terminalu:
   ```shell script
   docker-compose up -d
   ```
1. Uruchomienie może zająć do kilku minut.
1. Ostatecznie powinno pojawić się okno dialogowe z aplikacją ***Simple email client***

## Jak zatrzymać?
1. W głównym folderze projektu wpisz w terminalu:
    ```shell script
    docker-compose down
    ```

# EN SimpleEmailClient

## Description
The aim of the project is to implement the SMTP and POP3 protocols along with the implementation of a simple user interface. The project is carried out as part of the subject of Computer Systems Administration, AGH, WIET.

## Participants
1. Bartosz Kordek (kordek@student.agh.edu.pl)
2. Marcin Włodarczyk (mwlodarc@student.agh.edu.pl)
3. Grzegorz Zacharski (gzacharski@student.agh.edu.pl)

## Technologies
* Language: Java 11
* User interface: Swing library
* Containerization: Docker 
* Continuous integration (CI): GitHub Actions

## How to start?
1. You will need [Docker](https://www.docker.com/).
1. Clone or copy the project.
   * To clone the project you will need [Git](https://git-scm.com/).
   ```shell script
   git clone https://github.com/bartoszkordek/SimpleEmailClient.git
   ```
3. In root folder of the project type in terminal:
    ```shell script
    docker-compose up --build -d
    ```
1. Startup may last up to a few minutes.
1. Eventually you should see dialogue window with application ***Simple email client***

## How to stop?
1. In root folder of the project type in terminal:
    ```shell script
    docker-compose down
    ```
