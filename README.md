# Задача "Клиент-серверное приложение"

## Описание
После выполнения этого задания вы научитесь создавать сервер, способный принимать подключения.  
А также клиента, который будет подключаться к подобному серверу.

Создайте сервер с использованием ServerSocket.   
При входящем соединении на сервер, примите его, прочитайте из него строку. Выведите её на экран вместе с номером порта, с которого это соединение пришло.

Вместе с этим в другом методе (это может быть другой класс со вторым main методом или отдельный поток) подключитесь к этому серверу и отправьте ему одну строку.

## Реализация
Запускаем сервер на определенном порту и принимаем соединение:

```java
ServerSocket serverSocket = new ServerSocket(port); // порт можете выбрать любой в доступном диапазоне 0-65536. Но чтобы не нарваться на уже занятый - рекомендуем использовать около 8080
Socket clientSocket = serverSocket.accept(); // ждем подключения
PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
```

Читаем строку и выводим её на экран вместе с номером порта клиента, с которого пришло соединение:

```java
System.out.println("New connection accepted");

final String name = in.readLine();

out.println(String.format("Hi %s, your port is %d", name, clientSocket.getPort()));
```

Далее пишем клиента по примеру из презентации. Клиентом должна быть другая программа (просто ещё один метод main в другом классе, запускаемый параллельно, во время работы сервера) или второй поток. Обратите внимание, что хостом подключения будет `localhost` - это зарезервированный адрес для подключения к текущему компьютеру (то есть это ваш компьютер - там, где запущена программа). Также можно использовать `127.0.0.1` - это ip адрес, соответствующий доменному имени `localhost`  

*При проблемах связывайтесь с преподавателем и желаем вам успехов! ;)*

# Задача "Клиент-сервер с рюшечками"

## Описание
В этом задании вам предлагается более жизненный пример, где вы задействуете файл hosts, часто используемый при настройке окружения, и глубже погрузитесь во взаимодействие на сокетах.

Для выполнения задания нужно отредактировать файл `hosts`, чтобы запросы на адрес `netology.homework` перенаправлялись на ваш локальный компьютер (`127.0.0.1`)  
И усовершенствовать предыдущее задание, чтобы оно работало через адрес `netology.homework`, а общение происходило в несколько(3-5) шагов, в форме диалога  

## Реализация
Добавьте в файл `hosts` новую строку, в которой пропишите резолвинг `netology.homework` на ваш локальный компьютер (`127.0.0.1`). Подробную инструкцию по работе с этим файлом вы можете найти по ссылке в презентации.  

Далее усовершенствуйте первую задачу следующим образом:
1. В клиентской части замените адрес подключения `localhost` на `netology.homework`
2. В серверной и клиентской части сделайте последовательность вызовов в рамках одной сессии таким образом, чтобы получился некий диалог между клиентом и сервером, содержащий от 3 до 5 (или больше) шагов. Например,   
- при подключении сервер спрашивает: `Write your name`. Клиент читает это сообщение и отправляет ему имя.   
- Сервер читает имя, запоминает его, и задает следующий вопрос: `Are you child? (yes/no)`. Клиент отвечает `yes` или `no`.   
- На что сервер, если увидел `yes`, отвечает `Welcome to the kids area, %username%! Let's play!`, а если `no`, то `Welcome to the adult zone, %username%! Have a good rest, or a good working day!` (где %username% - имя клиента из предыдущего шага)

