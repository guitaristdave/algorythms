class ListNode:
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next


def reverse_list(head):
    prev = None
    current = head

    while current:
        next_node = current.next
        current.next = prev
        prev = current
        current = next_node

    return prev


def print_list(head):
    current = head

    while current:
        print(current.val, end=" ")
        current = current.next

    print()


# Создаем список: 1 -> 2 -> 3 -> 4 -> 5
head = ListNode(1)
head.next = ListNode(2)
head.next.next = ListNode(3)
head.next.next.next = ListNode(4)
head.next.next.next.next = ListNode(5)

print("Исходный список:")
print_list(head)

# Разворачиваем список
reversed_head = reverse_list(head)

print("Развёрнутый список:")
print_list(reversed_head)
