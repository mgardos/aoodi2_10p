# aoodi2_10p
Advanced Object Oriented Design II

GoF: http://www.uml.org.cn/c++/pdf/designpatterns.pdf

## Material
http://tinyurl.com/Objetos2-Octubre2019
SuperObjetos2

Accesos: arbolito, lindoarbol!, llevatiempo, eltiemponoesnada, remueve, verparaarriba, observando, meta-visitador

## Topics
- Idioms
- Patrones de diseño
- Excepciones
- Frameworks
- Metaprogramacion

Dia 1
=====

Modelo computable, o sea formal, que exprese el dominio de conocimiento (expresado en lenguaje natural, de manera ambigua).
El proceso de formacion del modelo es un proceso de aprendizaje, iterativo.
Para cada observacion, ente o cosa del dominio, se espera disponer de un objeto en el modelo computable.
Objeto hecho y derecho -> First class object
Reificar -> extraer a un objeto

### Idiom
Solucion de un lenguaje particular a un problema recurrente. Otros son patrones de diseño y frameworks.

Ejemplo

Codigo 1
```java
File orders = file.open("orders.txt");
try {
   ...
   file.write(...);
   ...
} finally {
   file.close();
}
```

Codigo 2
```java
File sales = file.open("sales.txt");
try {
   ...
   file.write(...);
   ...
   file.write(...);
   ...
} finally {
   file.close();
}
```

Refactor
```java
openDuring(filename, closure) {
   file = File.open(filename);
   try {
      closure.execute(file);
   } finally {
      file.close();
   }
}
```

Idiom
```java
file.openDuring('file.txt', file -> {
   ...
   file.write(...);
   ...
})
```

| Artefacto | Tipo      | Lenguage         | Dominio               |
|-----------|-----------|------------------|-----------------------|
| Idiom     | Concreta  | Dep. lenguaje    | No dominio particular |
| Patron    | Abstracta | No dep. lenguaje | No dominio particular |
| Framework | Concreta  | Dep. lenguaje    | Dominio particular    |

Los frameworks deben incluir inversion of control, de otro modo no son frameworks. Si no tiene esta caracteristica, es una libreria. Siempre se encuentran en el ambito tecnico, no los hay de negocio.

### Diseño de patrones
Empresa Tecktronics (Oregon) implemento Smalltalk 80, Cunningham (patrones a partir de Timeless way of building, arquitectura) y Beck (XP, TDD) trabajaron juntos en objetos, crearon CRC.
Johnson, Gamma, etc -> Gang of four -> Design patterns elements of reusable code (Ken Beck no participo porque los patrones se documentaron en forma academica)
Plop -> surgen conferencias de patrones de diseño, de las primeras se editaron libros
Para que un patron sea aceptado como tal, debe haber sido usado exitosamente en diversos contextos

Patron: Nombre, Intencion o proposito, Usos conocidos

Subclasificar para organizar conocimiento, por problemas esenciales, no para reutilizar codigo.

#### Decorator
Agregar funcionalidad ortogonal (no corresponde al dominio del problema que se decora) dinamicamente a un objeto. El decorator sabe responder los mismos metodos que el objeto que decora. Se pueden encadenar decorators y el orden puede importar, por lo cual se utiliza el patron builder para crear una cadena especifica de decoradores. Es polimorfico con el objeto que decora.
Con un lenguaje estaticamente tipado y codigo que no fue escrito para ser decorado, puede ser dificil implementar decorator.
En lenguajes de prototipacion (Javascript) el decorator no es una solucion ya que no existen problemas que se puedan resolver con decorator.

#### Adapter
Permitir que puedan conversar objetos que no estan preparados para eso, adapta interfaces.
No es polimorfico con el objeto que adapta, aunque es estructuralmente igual al decorator.

#### Proxy
Intermediario entre el usuario del objeto y el objeto real, controla el acceso al objeto real.
El proxy puede o no ser polimorfico con el objeto real.
El proxy es estructuralmente similar al decorator y al adapter.

Dia 2
=====

#### Composite
Trata al todo y a las partes de manera polimorfica. 
Conforma un grafo con estructura de arbol, los nodos estan compuestos por el objeto que no es composite.
El cuerpo recursivo esta dado por el composite, mientras que el fin de la recursion esta representado por los nodos.
Es necesario asegurar que el composite es un arbol, pero no un grafo con otro tipo de estructura, porque afecta la recursion (no ciclos, no nodos repetidos).
Son polimorficos porque responden a los mismos mensajes manteniendo la semantica, o sea que, el comportamiento es consistente.

                                      +--------------------+
                                      | SummarizingAccount |
                                      +--------------------+<------------+
                                      |                    |             |
                                      +--------------------+             |
                                                 ^                       |
                                                 |                       |
                                    +-----------------------+            |
                                    |                       |            |
    +-------------+       +------------------+        +-----------+      |
    | Transaction |<----<>| ReceptiveAccount |        | Portfolio |      |
    +-------------+       +------------------+        +-----------+<>----+
    |             |       |                  |        |           |
    +-------------+       +------------------+        +-----------+

GoF aconsejo mantener la misma interfaz para todas las partes por cuestions historicas, en aquella epoca se usaba C++ y esto evitaba que ocurriera violacion de acceso. Ademas trabajar con excepciones en C++ es complejo, se requiere smart pointers, por este motivo los metodos agregados por la interfaz tienen implementacion vacia. 

Sistema de tipos

| Typing | Estatico         | Dinamico                |
|--------|------------------|-------------------------|
| Strong | Java, C#, Eiffel | Smalltalk, Ruby, Python |
| Weak   | C, C++           | Visual Basic 6          |


Dia 3
=====

#### Method object
Extraer un metodo en una clase.

Dia 4
=====

#### Visitor
Method object mas double dispatch generico. Recorre un conjunto de objetos polimorficos. Respeta el principio open-close.

                                      +---------------------------+
                                      |       <<Interface>>       |
                                      | AccountTransactionVisitor |
                                      +---------------------------+
                                      | + visit(Deposit)          |
                                      | + visit(Withdraw)         |
                                      | + visit(TransferDeposit)  |
                                      | + visit(TransferWithdraw) |
                                      +---------------------------+
                                                    ^
                                                    |
                            +---------------------------------------------+
                            |                                             |
                +-----------------------+                     +------------------------+
                | AccountSummaryVisitor |                     | AccountTransferVisitor |
                +-----------------------+                     +------------------------+
                |                       |                     |                        |
                +-----------------------+                     +------------------------+

Sample of calling one of the visitors
```java
final AccountSummaryVisitor aVisitor = new AccountSummaryVisitor();
fromAccount.transactions().forEach(transaction -> transaction.accept(aVisitor));
```

                                      +-------------------------------------+
                                      |            <<Interface>>            |
                                      |          AccountTransaction         |
                                      +-------------------------------------+
                                      | + accept(AccountTransactionVisitor) |
                                      +-------------------------------------+
                                                         ^
                                                         |
                                  +----------------------+-------------------+
                                  |                      |                   |
                           +-------------+         +----------+         +---------+
                           | TransferLeg |         | Withdraw |         | Deposit |
                           +-------------+         +----------+         +---------+
                           |             |         |          |         |         |
                           +-------------+         +----------+         +---------+

Sample of calling one of the visitors
```java
public void accept(AccountTransactionVisitor aVisitor) {
    aVisitor.visit(this);
}
```

Dia 5
=====

#### Future
Es un proxy de ejecucion en paralelo

```java
public class Future<T> {
    private T value;
    private Thread thread;
    
    public Future(Supplier<T> aClosure) {
        thread = new Thread(() -> { value = aClosure.get(); });
        thread.start();
    }
    
    public T getValue() {
        try {
            thread.join();
        } catch (Exception e) {}
        return value;
    }
}
```


```
$ javac -cp C:/Users/usuario/.m2/repository/junit/junit/4.12/junit-4.12.jar src/main/java/ar/net/mgardos/*.java src/test/java/ar/net/mgardos/PortfolioTest.java -d target/classes/
```

En el classpath la ruta a los archivos .class se deben agregar luego de las rutas a los .jar.
```
$ java -cp .:/C/Users/usuario/.m2/repository/junit/junit/4.12/junit-4.12.jar:/C/Users/usuario/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar:target/classes/ org.junit.runner.JUnitCore ar.net.mgardos.PortfolioTest
```

### Excepciones
Es el patron usado para evitar codigo repetido resultante del uso de la tecnica de codigo de error. No hay razon para no hacer uso de excepciones, no es verdad que su uso lentifique la implementacion, esto depende de las implementaciones, del codigo del usuario y del lenguaje.

Explicacion conceptual de excepciones: contratos. Hay contratos explicitos e implicitos.
1) Cuando no se cumple un contrato se genera una excepcion
2) Responsable de generar la excepcion
3) Que tipo de excepcion se genera
4) Responsable de atender la excepcion
5) Como atender a dicha excepcion

La excepcion se debe generar inmediatamente que se rompe el contrato.
Responsable de verificar el contrato, si se cumple
- lisp/mit: receptor del mensaje (ej. se pasa indice invalido, el array genera excepcion)
- c/standard: emisor del mensaje debe validar antes de colaborar (el usuario del array debe verificar que el indice el valido, de otro modo el comportamiento del array es indeterminado)

la escuela lisp/mit es la mas adecuada para evitar, por ejemplo, problemas de seguridad.

El manejo de la excepciones se debe realizar en la raiz o cerca de la raiz en el arbol de ejecucion. El rol de las hojas del arbol de ejecucion es generar excepciones, si corresponde.

Si el error es recuperable, tiene sentido generar una excepcion esecifica.

Dia 6
=====

Es dudoso reemplazar una sentencia IF cuando los colaboradores corresponden a distintos dominios, como es el caso de una validacion sobre un numero que realiza un colaborador del dominio de interes.

#### State
El colaborador que esta compuesto por el estado delega el dicho estado sus interacciones con colaboradores externos. A pesar de esto, ambos no son polimorficos. El estado utiliza double dispatch para evitar romper encapsulamiento accediendo al estado de colaborador que esta compuesto por el estado. El estado decide que hacer pero no lo hace, le indica al colaborador que debe hacer.
El estado debe conocer de quien es estado, y no debe ser singleton ya que genera acoplamiento global lo cual impide poder parametrizar, pruebas unitarias. Es una instancia de un automata deterministico, es una maquina de estados.

     +-----------------+             +---------------------+
     |    TicTacToe    |             |    TicTacToeState   |
     +-----------------+<>---------->+---------------------+
     |                 |             |                     |
     +-----------------+             +---------------------+
                                                ^
                                                |
                 +-------------+----------------+----------------+-------------+
                 |             |                |                |             |
            +--------+     +--------+      +---------+      +--------+     +--------+
            |  WinX  |     |  WinO  |      |  PlayX  |      |  PlayO |     |  Tied  |
            +--------+     +--------+      +---------+      +--------+     +--------+
            |        |     |        |      |         |      |        |     |        |
            +--------+     +--------+      +---------+      +--------+     +--------+

### Metaprogramacion
Verificar que un visitor es implementado correctamente. Definir reglas respecto a como implementar un visitor correctamente.
