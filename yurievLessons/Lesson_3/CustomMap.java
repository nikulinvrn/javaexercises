package yurievLessons.Lesson_3;

import java.util.Objects;

public class CustomMap<K, V> {
    final int DEFAULT_CAPACITY = 16;

    private Node<K, V>[] table;

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
        int index = hash & (DEFAULT_CAPACITY - 1);
        if (table == null) {
            table = new Node[DEFAULT_CAPACITY];
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
                    thisNode = new Node<>(hash, key, value, thisNode.getNext()); // если ключ существует — переписываем ноду
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
        } // TODO: реализовать сортировку. Сортировку проводить после добавления элемента.

        return false;
    }

    public boolean putAll(CustomMap<K, V> inputMap) {
        // TODO: реализовать итератор для CustomMap или найти иной способ перебрать все элементы входящей мапы
    return false;
    }


    /* ----------------  «RETRIEVE» OPERATIONS -------------- */

    /**
     * @return количество элементов (пар ключ : значение) в таблице
     */
    public int size() {
        return this.size;
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


    /* ----------------  «UPDATE» OPERATIONS -------------- */



    /* ----------------  «DELETE» OPERATIONS -------------- */


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
                sb.append("}");
            }
        }
        return sb.toString();
    }

    private static class Node<K, V> {
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
            if (!(o instanceof Node<?, ?> node)) return false;
            return hash == node.hash && Objects.equals(getKey(), node.getKey()) && Objects.equals(getValue(), node.getValue());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getKey());
        }
    }
}







