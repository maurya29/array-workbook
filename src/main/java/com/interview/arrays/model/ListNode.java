package com.interview.arrays.model;

/** Shared node for the linked-list exercises within Two Pointers and Sorting. */
public class ListNode {
    public int val;
    public ListNode next;

    public ListNode(int val) { this(val, null); }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}
