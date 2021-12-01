package com.fidget.game.DataStructures;

/**
 * This class will be the data structure used to store PointAndTime class.
 * <p>
 * Created by leej431 on 6/20/17.
 */

public class CircularLinkedList<T> {

    public int size = 0;
    public Node head = null;
    public Node tail = null;

    //add a new node at the start of the linked list
    public void addNodeAtStart(T data) {
        //System.out.println("Adding node " + data + " at start");
        Node n = new Node(data);
        if (size == 0) {
            head = n;
            tail = n;
            n.next = head;
        } else {
            Node temp = head;
            n.next = temp;
            head = n;
            tail.next = head;
        }
        size++;
    }

    public void addNodeAtEnd(T data) {
        if (size == 0) {
            addNodeAtStart(data);
        } else {
            Node n = new Node(data);
            tail.next = n;
            tail = n;
            tail.next = head;
            size++;
        }
    }

    public void deleteNodeFromStart() {
        if (size == 0) {
            //System.out.println("\nList is Empty");
        } else {
            head = head.next;
            tail.next = head;
            size--;
        }
    }

    public Node popNodeFromStart() {
        Node<T> retNode = null;
        if (size == 0) {
            //System.out.println("\nList is Empty");
        } else {
            retNode = head;
            head = head.next;
            tail.next = head;
            size--;
        }
        return retNode;
    }

    public Object elementAt(int index) {
        if (index > size) {
            return null;
        }
        Node n = head;
        while (index - 1 != 0) {
            n = n.next;
            index--;
        }
        return n.data;
    }

    //print the linked list
    public void print() {
        System.out.print("Circular Linked List:");
        Node temp = head;
        if (size <= 0) {
            System.out.print("List is empty");
        } else {
            do {
                System.out.print(" " + temp.data);
                temp = temp.next;
            }
            while (temp != head);
        }
    }

    //get Size
    public int getSize() {
        return size;
    }

    class Node<T> {
        T data;
        Node next;

        public Node(T data) {
            this.data = data;
        }

        public boolean eqauls(Node<T> other) {
            return data.equals(other.data);
        }
    }
}