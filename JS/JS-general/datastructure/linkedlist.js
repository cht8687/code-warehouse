function node(data) {
	this.value = data
}

function linkedList() {
	this.head = null,
  this.next = null
}

linkedList.prototype.add = function(node) {
	if (this.head == null) {
  	this.head = node;
  } else {
  	if (this.next == null) {
    	this.next = node;
    } else {
    	  var current = this.next;
        while(current.next != null) {
          current = current.next;
        }
        current.next = node;
      }
    }
}

var node1 = new node(1);
var node2 = new node(3);
var node3 = new node(3);
var node4 = new node(777);
var node4 = new node(777);
var node4 = new node(66);
var node4 = new node(23);
var node4 = new node(3);

var myLinkedList = new linkedList();
myLinkedList.add(node1);
myLinkedList.add(node2);
myLinkedList.add(node3);
myLinkedList.add(node4);



// Write code to remove duplicates from an unsorted linked list.

function removeDuplicate() {
  

}