package   task2028;

import java.io.Serializable;
import java.util.*;

/*
Построй дерево(1)
*/
public class CustomTree extends AbstractList<String> implements Cloneable, Serializable {

    Entry<String> root = new CustomTree.Entry<String>("0");


    public CustomTree() {
    }

    @Override
    public int size() {
        int sizeTree = 0;

        Queue<Entry<String>> queue = new LinkedList<>();

        Entry<String> top = root;

        do {

            if (top.leftChild != null) queue.add(top.leftChild);
            if (top.rightChild != null) queue.add(top.rightChild);
            if (!queue.isEmpty()) {
                top = queue.poll();
                sizeTree++;
            }
            // System.out.println(top.elementName);
        } while (!queue.isEmpty());

        return sizeTree;
    }

    @Override
    public String get(int index) {
        throw new UnsupportedOperationException();
        // return remove( "" + index);
    }

    @Override
    public String set(int index, String element) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void add(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        throw new UnsupportedOperationException();
    }


    public boolean remove(Object o) {
        try {
            String elementToRemoveName = (String) o;

            Queue<Entry<String>> queue = new LinkedList<>();

            Entry<String> top = root;
            boolean elementFound = false;
            String partnName = null;
            do {
                if (top.elementName.equals(elementToRemoveName)) {
                    if (top.isLeftElement) {
                        if ((top.parent.leftChild != null))
                            top.parent.clearLeftChild();
                    } else  {
                        if ((top.parent.rightChild != null))
                            top.parent.clearRightChild();
                    }
                    elementFound = true;

                }

                if (top.leftChild != null) queue.add(top.leftChild);
                if (top.rightChild != null) queue.add(top.rightChild);
                if (!queue.isEmpty()) top = queue.poll();

               /* if (top.elementName.equals(elementToRemoveName)) {
                    if (top.isLeftElement) {
                        if ((top.parent.leftChild != null))
                            top.parent.clearLeftChild();
                    } else {
                        if ((top.parent.rightChild != null))
                            top.parent.clearRightChild();
                    }
                    elementFound = true;

                }*/

            } while (!elementFound && !queue.isEmpty());
        } catch (Exception e) {
            throw new UnsupportedOperationException();
        }
        printAllTree();

        return true;
    }

    @Override
    public String remove(int index) {
        throw new UnsupportedOperationException();
    }


    @Override
    public List<String> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();

    }


    @Override
    public boolean add(String elementName) {
        //поиск первого свободного элемента c учетом уровня дерева
        //создание элемента
        //задание его родителя
        if (noFreePlacesInTree()) {
            clearPlacesOnTree();
        }

        // оствободить все ветви от блоков
        addElement(elementName);
        //добавление в Лист

        return true;
    }

    private void clearPlacesOnTree() {
        Queue<Entry<String>> queue = new LinkedList<>();

        Entry<String> top = root;

        do {

            if (top.leftChild != null) {
                queue.add(top.leftChild);
            } else top.availableToAddLeftChildren = true;
            if (top.rightChild != null) {
                queue.add(top.rightChild);
            } else top.availableToAddRightChildren = true;
            if (!queue.isEmpty()) top = queue.poll();

        } while (!queue.isEmpty());
    }

    private boolean noFreePlacesInTree() {
        boolean rezult = true;
        Queue<Entry<String>> queue = new LinkedList<>();

        Entry<String> top = root;

        do {
            if (top.isAvailableToAddChildren()) rezult = false;
            //top.treatment();
            if (top.leftChild != null) queue.add(top.leftChild);
            if (top.rightChild != null) queue.add(top.rightChild);
            if (!queue.isEmpty()) top = queue.poll();

        } while (!queue.isEmpty());
        return rezult;
    }

    void addElement(String nextElementName) {
        Queue<Entry<String>> queue = new LinkedList<>();

        Entry<String> top = root;
        boolean elementAdded = false;
        do {
            if (top.isAvailableToAddChildren()) {
                Entry<String> nextElement = new Entry(nextElementName);
                nextElement.setParent(top);
                int nexElementLineNumber = top.getLineNumber() + 1;
                nextElement.setLineNumber(nexElementLineNumber);

                if (top.isAvailableToAddLeftChildren()) {
                    top.leftChild = nextElement;
                    top.leftChild.isLeftElement = true;
                } else if (top.isAvailableToAddRightChildren()) {
                    top.rightChild = nextElement;
                    top.rightChild.isLeftElement = false;
                }
                nextElement.checkChildren();
                top.checkChildren();

                elementAdded = true;
                queue.clear();
            } else {
                //top.treatment();
                if (top.leftChild != null) queue.add(top.leftChild);
                if (top.rightChild != null) queue.add(top.rightChild);
                if (!queue.isEmpty()) top = queue.poll();
            }

        } while (!elementAdded && !queue.isEmpty());
    }

    public void printAllTree() {
        Queue<Entry<String>> queue = new LinkedList<>();

        Entry<String> top = root;

        do {


            //top.treatment();
            if (top.leftChild != null) queue.add(top.leftChild);
            if (top.rightChild != null) queue.add(top.rightChild);
            if (!queue.isEmpty()) top = queue.poll();
           /* System.out.println(top.lineNumber);
            System.out.println(top.elementName + " parent : " + top.parent.elementName);*/

        } while (!queue.isEmpty());
    }

    public String getParent(String elementName) {
        Queue<Entry<String>> queue = new LinkedList<>();

        Entry<String> top = root;
        boolean elementFound = false;
        String partnName = null;
        do {
            if (top.elementName.equals(elementName)) {
                partnName = top.parent.elementName;

                elementFound = true;
                queue.clear();
            } else {
                //top.treatment();
                if (top.leftChild != null) queue.add(top.leftChild);
                if (top.rightChild != null) queue.add(top.rightChild);
                if (!queue.isEmpty()) top = queue.poll();
            }
            if (top.elementName.equals(elementName)) {
                partnName = top.parent.elementName;

                elementFound = true;
                queue.clear();
            }

        } while (!elementFound && !queue.isEmpty());
        return partnName;

    }


    static class Entry<T> implements Serializable {
        String elementName;
        int lineNumber;
        boolean availableToAddLeftChildren, availableToAddRightChildren, isLeftElement;
        CustomTree.Entry<T> parent, leftChild, rightChild;

        public Entry(String line) {
            this.elementName = line;
            this.availableToAddLeftChildren = true;
            this.availableToAddRightChildren = true;
        }

        public void setParent(CustomTree.Entry<T> parent) {
            this.parent = parent;
        }

        public void setElementName(String elementName) {
            this.elementName = elementName;
        }

        public void setLineNumber(int lineNumber) {
            this.lineNumber = lineNumber;
        }

        public String getElementName() {

            return this.elementName;
        }

        public int getLineNumber() {
            return this.lineNumber;
        }

        public CustomTree.Entry<T> getParent() {
            return this.parent;
        }

        void checkChildren() {
            if (this.leftChild != null) this.availableToAddLeftChildren = false;
            if (this.rightChild != null) this.availableToAddRightChildren = false;
        }

        public boolean isAvailableToAddChildren() {
            return (this.availableToAddLeftChildren || this.availableToAddRightChildren);
        }

        public boolean isAvailableToAddLeftChildren() {
            return this.availableToAddLeftChildren;
        }

        public boolean isAvailableToAddRightChildren() {
            return this.availableToAddRightChildren;
        }

        public void clearRightChild() {
            this.rightChild = null;
            //this.availableToAddLeftChildren = false;
        }

        public void clearLeftChild() {
            this.leftChild = null;
            // this.availableToAddRightChildren = false;
        }
    }
}
