package yurievLessons.Lesson_3;

// TODO: добавить к сравнению еще и проверку ключей, а не только хешей


import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class CustomMap<K, V> implements Iterable<CustomMap.Node<K, V>> {
    final int DEFAULT_CAPACITY = 16;

    private Node<K, V>[] table;

    //на случай реализации авторесайза индексной таблицы
    private int currentCapacity = DEFAULT_CAPACITY;
    private int size;

    /**
     * Инициализирует пустую таблицу пар "ключ : значение"
     */
    public CustomMap() {
        this.size = 0;
    }


    /* ----------------  «CREATE» OPERATIONS -------------- */

    /**
     * Добавляет в объект класса {@code CustomMap} узел {@code key} : {@code value}.
     * Если ключ уже существует, то существующий узел {@code key : value} будет заменен добавляемым.
     *
     * @param key   ключ
     * @param value значение
     * @return {@code true} если добавление прошло успешно, {@code false} если добавление не удалось
     */
    public boolean put(K key, V value) {
        int hash = key.hashCode();
        int index = hash & (currentCapacity - 1);
        if (table == null) {
            table = new Node[currentCapacity];
        }
        Node<K, V> thisNode = table[index];
        Node<K, V> prevNode = null;

        if (table[index] == null) {
            table[index] = new Node<>(hash, key, value, null); // если индекс пуст — создаем ноду
            size++;
            return true;
        } else {
            while (thisNode != null) {
                if (thisNode.getKey().hashCode() == hash) {
                    thisNode = new Node<>(hash, key, value, thisNode.getNext()); // если хеш совпадает — переписываем ноду
                                                                                 // вероятна проблема совпадения хешей при разных ключах!
                    if (prevNode != null) {
                        prevNode.setNext(thisNode);
                    }
                    return true;
                } else if (thisNode.getNext() == null) {
                    thisNode.setNext(new Node<>(hash, key, value, null));
                    size++;
                    return true;
                }
                prevNode = thisNode;
                thisNode = thisNode.getNext();
            }
        }
        // TODO: реализовать сортировку. Сортировку проводить после или во время добавления элемента?
        //       После сортировки реализовать метод бинарного поиска требуемой ноды.

        /*       По идее, сортировку можно внедрить начиная с 59 стоки.
                 Перед проверкой на пустоту следующей позиции проверяем больше ли хеш вставляемого значения.
                 Если хеш вставляемого значения больше, то идем дальше и проверяем на пустоту следующую позицию.
                 Если хеш вставляемого значения меньше, то в предыдущую ноду вписываем вставляемую, а в вставляемую -
                 записываем ссылку на следующую ноду, хеш которой больше.
                 */

        return false;
    }

    /**
     * Метод добавляет в мапу, на которой вызван, все элементы из мапы, которая передается ему в качестве параметра.
     * Добавление происходит согласно логике метода {@link CustomMap#put}.
     *
     * @param inputMap мапа, которую будем помещать в ту мапу, на которой вызван метод
     * @return true если добавление успешно, false если добавление не произошло
     */
    public boolean putAll(CustomMap<K, V> inputMap) {
        int countModification = 0;
        for (int i = 0; i < inputMap.table.length; i++) {
            Node<K, V> thisNode = inputMap.table[i];
            while (thisNode != null) {
                this.put(thisNode.getKey(), thisNode.getValue());
                thisNode = thisNode.getNext();
                countModification++;
            }
        }

        return (countModification > 0) ? true : false;
    }

    /**
     * Помещает в коллекцию новый объект с ключом key и значением value,
     * если в коллекции еще нет элемента с подобным ключом.
     *
     * @param key   ключ
     * @param value значение
     * @return true если добавление состоялось, false в ином случае
     */
    public boolean putIfAbsent(K key, V value) {
        if (!this.containsKey(key)) {
            this.put(key, value);
            return true;
        } else {
            return false;
        }
    }

    /* ----------------  «RETRIEVE» OPERATIONS -------------- */

    /**
     * @return количество элементов (пар ключ : значение) в таблице
     */
    public int size() {
        return this.size;
    }

    /**
     * @return true если мапа пустая (null или size = 0), false в любом ином случае
     */
    public boolean isEmpty() {
        return (this == null || this.size() == 0) ? true : false;
    }

    /**
     * @param key ключ
     * @return значение, соответствующее ключу "key" или "null", если ключа не существует
     */
    public V getValue(K key) {
        int hash = key.hashCode();
        for (Node node : table) {
            if (node == null) {
                continue;
            }
            Node<K, V> temp = node;
            while (temp != null) {
                if (temp.getKey().hashCode() == hash) {
                    return (V) temp.getValue();
                }
                temp = temp.getNext();
            }
        }

        return null;
    }

    /**
     * Метод проверяет на существование ноды в таблице с ключом key
     *
     * @param key ключ, по которому проверяем существование ноды
     * @return true если нода по ключу найдена, false в ином случае
     */
    public boolean containsKey(K key) {
        int hash = key.hashCode();
        for (int i = 0; i < table.length; i++) {
            Node<K, V> thisNode = table[i];
            if (thisNode == null) {
                continue;
            }
            while (thisNode != null) {
                if (thisNode.getKey().hashCode() == hash) {
                    return true;
                }
                thisNode = thisNode.getNext();
            }
        }

        return false;
    }

    /**
     * Метод проверяет на существование ноды в таблице со значением value
     *
     * @param value значение, по которому проверяем существование ноды
     * @return true если нода по ключу найдена, false в ином случае
     */
    public boolean containsValue(V value) {
        for (int i = 0; i < table.length; i++) {
            Node<K, V> thisNode = table[i];
            if (thisNode == null) {
                continue;
            }
            while (thisNode != null) {
                if (thisNode.getValue().equals(value)) {
                    return true;
                }
                thisNode = thisNode.getNext();
            }
        }

        return false;
    }

    /**
     * Возвращает коллекцию ключей
     *
     * @return Set keySet
     */
    public Set keySet() {
        Set keys = new HashSet();

        for (int i = 0; i < table.length; i++) {
            Node<K, V> thisNode = table[i];
            if (thisNode == null) {
                continue;
            }
            while (thisNode != null) {
                keys.add(thisNode.getKey());
                thisNode = thisNode.getNext();
            }
        }

        return keys;
    }

    /**
     * Возвращает коллекцию значений из нод
     *
     * @return Set valuesSet
     */
    public Set<V> valueSet() {
        Set<V> values = new HashSet<>();

        for (int i = 0; i < table.length; i++) {
            Node<K, V> thisNode = table[i];
            if (thisNode == null) {
                continue;
            }
            while (thisNode != null) {
                values.add(thisNode.getValue());
                thisNode = thisNode.getNext();
            }
        }

        return values;
    }



    /* ----------------  «UPDATE» OPERATIONS -------------- */



    /* ----------------  «DELETE» OPERATIONS -------------- */

    /**
     * Метод затирает мапу до значения null
     */
    public void clear() {
        this.table = null;
    }

    /**
     * Метод удаляет пару "ключ : значение" по ключу
     *
     * @param key ключ, по которому ищем пару
     * @return true если удаление прошло успешно, false в любом ином случае
     */
    public boolean removeByKey(K key) {
        int hash = key.hashCode();
        for (int i = 0; i < table.length; i++) {
            Node<K, V> thisNode = table[i];
            Node<K, V> prevNode = null;

            if (thisNode == null) {
                continue;
            }
            while (thisNode != null) {
                if (thisNode.getKey().hashCode() == hash) {
                    if (prevNode != null) {
                        prevNode.setNext(thisNode.getNext());
                        return true;
                    } else if (prevNode == null) {
                        table[i] = table[i].getNext();
                        return true;
                    }
                }
                prevNode = thisNode;
                thisNode = thisNode.getNext();
            }
        }

        return false;
    }

    /**
     * Метод удаляет ноду с первым обнаруженным вхождением значения
     *
     * @param value значение, по которому отбирается нода на удаление
     * @return true если удаление завершено успешно, false в ином случае
     */
    public boolean removeByValue(V value) {
        // TODO: обдумать вариант с удалить последнее вхождение
        for (int i = 0; i < table.length; i++) {
            Node<K, V> thisNode = table[i];
            Node<K, V> prevNode = null;
            if (thisNode == null) {
                continue;
            }
            while (thisNode != null) {
                if (thisNode.getValue().equals(value)) {
                    if (prevNode != null) {
                        prevNode.setNext(thisNode.getNext());
                        return true;
                    } else if (prevNode == null) {
                        table[i] = table[i].getNext();
                        return true;
                    }
                }
                prevNode = thisNode;
                thisNode = thisNode.getNext();
            }
        }

        return false;
    }

    /**
     * Метод удаляет ноды с соотсветсвующим значением
     *
     * @param value значение, по которому отбираются ноды на удаление
     * @return true если удаление завершено успешно один и более раз, false в ином случае
     */
    public boolean removeAllByValue(V value) {
        int countRemoved = 0;
        for (int i = 0; i < table.length; i++) {
            Node<K, V> thisNode = table[i];
            Node<K, V> prevNode = null;
            if (thisNode == null) {
                continue;
            }
            while (thisNode != null) {
                if (thisNode.getValue().equals(value)) {
                    if (prevNode != null) {
                        prevNode.setNext(thisNode.getNext());
                        countRemoved++;
                    } else if (prevNode == null) {
                        table[i] = table[i].getNext();
                        countRemoved++;
                    }
                }
                prevNode = thisNode;
                thisNode = thisNode.getNext();
            }
        }

        return countRemoved > 0;
    }

    // TODO: переопределить equals() и hashCode() для CustomMap

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CustomMap:\n");
        for (Node node : table) {
            if (node != null) {
                Node<K, V> thisNodeInIndex = node;
                sb.append("{");
                while (thisNodeInIndex != null) {
                    sb.append(thisNodeInIndex);
                    thisNodeInIndex = thisNodeInIndex.getNext();
                }
                sb.append("}\n");
            }
        }
        return sb.toString();
    }

    @Override
    public Iterator iterator() {
        return new Iterator(this);
    }

    class Iterator implements java.util.Iterator<Node<K, V>> {

        Node<K, V> currentNode;
        int currentIndex;
        boolean firstStep = true; /* флаг захода в мапу, костыль для решения проблемы
                                     "стартовая позиция должна указать на индекс [-1]" */

        public Iterator(CustomMap<K, V> map) {
            //todo: как обработать случай, когда map == null? По идее, это
            //     "исключительное поведение", а значит должно выбрасываться
            //      исключение типа NullPointerException или около того
            this.currentIndex = 0;
            this.currentNode = table[currentIndex];
        }

        @Override
        public boolean hasNext() {
            if (currentNode != null && currentNode.getNext() != null) {
                return true;
            } else {
                if (!firstStep) {
                    currentIndex++;
                }
                for (int i = currentIndex; i < currentCapacity; i++) {
                    if (table[i] != null) {
                        currentIndex = i;
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public Node<K, V> next() {
            if (firstStep && currentNode != null) {
                firstStep = false;
                return currentNode;
            } else {
                firstStep = false;
            }
            if (currentNode != null && currentNode.getNext() != null) {
                currentNode = currentNode.getNext();
            } else {
                for (int i = currentIndex; i < currentCapacity; i++) {
                    if (table[i] != null) {
                        currentIndex = i;
                        break;
                    }
                }
                currentNode = table[currentIndex];
            }

            return currentNode;
        }
    }

    static class Node<K, V> {
        final int hash;
        final K key;
        V value;
        Node<K, V> next;

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        private Node<K, V> getNext() {
            return this.next;
        }

        private void setNext(Node<K, V> node) {
            this.next = node;
        }

        public String toString() {
            return "[" + key + " = " + value + "]";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node<?, ?> node = (Node<?, ?>) o;
            return hash == node.hash && Objects.equals(key, node.key) && Objects.equals(value, node.value) && Objects.equals(next, node.next);
        }

        @Override
        public int hashCode() {
            return Objects.hash(hash, key, value);
        }
    }
}







