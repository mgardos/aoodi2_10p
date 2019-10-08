# aoodi2_10p
Advanced Object Oriented Design II

## Material
http://tinyurl.com/Objetos2-Octubre2019
SuperObjetos2

Accesos: arbolito, lindoarbol!

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
