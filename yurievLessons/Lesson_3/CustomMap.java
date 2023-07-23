package yurievLessons.Lesson_3;

import java.util.*;

public class CustomMap<K, V> implements Iterable<CustomMap.Node<K, V>> {
    final int DEFAULT_CAPACITY = 16;
    private Node<K,V>[] table;
    private int currentCapacity = DEFAULT_CAPACITY;
    private int size;
    // необходимо для реализации бинарного поиска
    private int[] numberOfNodesInIndexes = new int[currentCapacity];

    /**
     * Инициализирует пустую таблицу пар "ключ : значение"
     */
    public CustomMap() {
        this.table = new Node[currentCapacity];
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
        Node<K, V> currentNode = table[index];

        if (table[index] == null) {
            table[index] = new Node<>(hash, key, value, null);
            size++;
            numberOfNodesInIndexes[index]++;
            return true;
        }
        while (currentNode != null) {
            int currentNodeHash = currentNode.getKey().hashCode();
            if (hash == currentNodeHash && currentNode.getKey().equals(key)) {
                currentNode = new Node<>(hash, key, value, currentNode.getNext());
                return true;
            }

            if (hash < currentNodeHash) {
                currentNode = new Node<>(hash, key, value, currentNode);
                size++;
                numberOfNodesInIndexes[index]++;
                return true;
            }
            if (hash > currentNodeHash && currentNode.getNext() == null) {
                currentNode.setNext(new Node<>(hash, key, value, null));
                size++;
                numberOfNodesInIndexes[index]++;
                return true;
            }
            if (hash > currentNodeHash && currentNode.getNext() != null
                    && hash < currentNode.getNext().getKey().hashCode()) {
                currentNode.setNext(new Node<>(hash, key, value, currentNode.getNext()));
                size++;
                numberOfNodesInIndexes[index]++;
                return true;
            }

            currentNode = currentNode.getNext();
        }

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

        return countModification > 0;
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
        return this == null || this.size() == 0;
    }

    /**
     * @param key ключ
     * @return значение, соответствующее ключу "key" или "null", если ключа не существует
     */
    public V getValue(K key) {
        int hash = key.hashCode();
        for (Node<K, V> node : table) {
            if (node == null) {
                continue;
            }
            Node<K, V> temp = node;
            while (temp != null) {
                if (temp.getKey().hashCode() == hash) {
                    return temp.getValue();
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
        for (Node<K, V> rootNode : table) {
            Node<K, V> currentNode = rootNode;
            if (currentNode == null) {
                continue;
            }
            while (currentNode != null) {
                if (currentNode.getKey().hashCode() == hash) {
                    return true;
                }
                currentNode = currentNode.getNext();
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
        for (Node<K, V> rootNode : table) {
            Node<K, V> currentNode = rootNode;
            if (currentNode == null) {
                continue;
            }
            while (currentNode != null) {
                if (currentNode.getValue().equals(value)) {
                    return true;
                }
                currentNode = currentNode.getNext();
            }
        }

        return false;
    }

    /**
     * Возвращает коллекцию ключей
     *
     * @return Set keySet
     */
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();

        for (Node<K, V> rootNode : table) {
            Node<K, V> currentNode = rootNode;
            if (currentNode == null) {
                continue;
            }
            while (currentNode != null) {
                keys.add(currentNode.getKey());
                currentNode = currentNode.getNext();
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

        for (Node<K, V> rootNode : table) {
            Node<K, V> currentNode = rootNode;
            if (currentNode == null) {
                continue;
            }
            while (currentNode != null) {
                values.add(currentNode.getValue());
                currentNode = currentNode.getNext();
            }
        }

        return values;
    }



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
                        size--;
                        numberOfNodesInIndexes[i]--;
                        return true;
                    } else if (prevNode == null) {
                        table[i] = table[i].getNext();
                        size--;
                        numberOfNodesInIndexes[i]++;
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
                        size--;
                        numberOfNodesInIndexes[i]--;
                        return true;
                    } else if (prevNode == null) {
                        table[i] = table[i].getNext();
                        size--;
                        numberOfNodesInIndexes[i]--;
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
     * Метод удаляет ноды с соответствующим значением
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
                        size--;
                        numberOfNodesInIndexes[i]--;
                        countRemoved++;
                    } else if (prevNode == null) {
                        table[i] = table[i].getNext();
                        size--;
                        numberOfNodesInIndexes[i]--;
                        countRemoved++;
                    }
                }
                prevNode = thisNode;
                thisNode = thisNode.getNext();
            }
        }

        return countRemoved > 0;
    }


    /*-------------------------------< UTILITIES >-------------------------------*/


    /**
     * Метод реализует бинарный поиск по ключу в рамках каждого листа,
     * в индексах базового массива.
     * <p>
     * Абсолютно неадекватная наркомания с точки зрения применимости.
     * Но раз надо... "Пункт 1: Старший по званию всегда прав.
     *                 Пункт 2: Если старший по званию не прав - см. п. 1"
     *
     * @param foundKey ключ, по которому ищем ноду
     * @return искомую ноду или null, если ничего не нашел
     */
    public Node<K, V> binaryFindByKeyInIndexes(K foundKey) {
        Node<K, V> indexNode = table[foundKey.hashCode() & (currentCapacity - 1)];
        ArrayList<Node<K, V>> indexList = new ArrayList<>();
        do {
            indexList.add(indexNode);
            indexNode = indexNode.getNext();
        } while (indexNode != null);

        int start = 0;
        int end = indexList.size() - 1;
        while (start <= end) {
            int middle = (start + end) / 2;
            if (indexList.get(middle).getKey().hashCode() == foundKey.hashCode()) {
                return indexList.get(middle);
            }
            if (indexList.get(middle).getKey().hashCode() > foundKey.hashCode()) {
                end = middle - 1;
            }
            if (indexList.get(middle).getKey().hashCode() < foundKey.hashCode()) {
                start = middle + 1;
            }
        }

        return null;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomMap<?, ?> customMap = (CustomMap<?, ?>) o;
        return currentCapacity == customMap.currentCapacity
                && size == customMap.size
                && Arrays.equals(table, customMap.table)
                && Arrays.equals(numberOfNodesInIndexes, customMap.numberOfNodesInIndexes);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(currentCapacity, size);
        result = 31 * result + Arrays.hashCode(table);
        result = 31 * result + Arrays.hashCode(numberOfNodesInIndexes);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CustomMap:\n");
        for (Node<K, V> node : table) {
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
            //  todo: как обработать случай, когда map == null? По идее, это
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







