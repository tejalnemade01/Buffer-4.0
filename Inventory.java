import java.util.*;
public class Inventory {
private static TreeNode root;
private Map<Integer, Integer> cart;
private static class Product {
private int id;
private String name;
private double price;
public Product(int id, String name, double price) {
this.id = id;
this.name = name;
this.price = price;
}
public int getId() {
return id;
}
public String getName() {
return name;
}
public double getPrice() {
return price;
}
@Override
public String toString() {
return String.format("%d: %s ($%.2f)", id, name, price);
}
private Product findProductById(TreeNode root, int productId) {
if (root == null) {
return null;
} else if (root.product.getId() == productId) {
return root.product;
} else if (productId < root.product.getId()) {
return findProductById(root.left, productId);
} else {
return findProductById(root.right, productId);
}
}
}
private static class TreeNode {
private Product product;
private TreeNode left;
private TreeNode right;
TreeNode root=null;
public TreeNode(Product product) {
this.product = product;
}
}
public Inventory() {
cart = new HashMap<>();
}
public void addProduct(Product product) {
if (root == null) {
root = new TreeNode(product);
} else {
addProductHelper(root, product);
}
}
private void addProductHelper(TreeNode node, Product product) {
if (product.getId() < node.product.getId()) {
if (node.left == null) {
node.left = new TreeNode(product);
} else {
addProductHelper(node.left, product);
}
} else {
if (node.right == null) {
node.right = new TreeNode(product);
} else {
addProductHelper(node.right, product);
}
}
}
public void searchProducts(String query) {
List<Product> results = new ArrayList<>();
searchProductsHelper(root, query, results);
if (results.isEmpty()) {
System.out.println("No results found.");
} else {
System.out.println("Search results:");
for (Product product : results) {
System.out.println(product);
}
}
}
private void searchProductsHelper(TreeNode node, String query, List<Product> results) {
if (node != null) {
searchProductsHelper(node.left, query, results);
if (node.product.getName().toLowerCase().contains(query.toLowerCase())) {
results.add(node.product);
}
searchProductsHelper(node.right, query, results);
}
}
public void deleteProduct(int id) {
root = deleteProductHelper(root, id);
}
private TreeNode deleteProductHelper(TreeNode node, int id) {
if (node == null) {
return null;
} else if (id < node.product.getId()) {
node.left = deleteProductHelper(node.left, id);
} else if (id > node.product.getId()) {
node.right = deleteProductHelper(node.right, id);
} else {
// node with the ID has been found
if (node.left == null && node.right == null) {
// node has no children
node = null;
} else if (node.left == null) {
// node has one child (right)
node = node.right;
} else if (node.right == null) {
// node has one child (left)
node = node.left;
} else {
// node has two children
TreeNode temp = findMinNode(node.right);
node.product = temp.product;
node.right = deleteProductHelper(node.right, temp.product.getId());
}
}
return node;
}
private TreeNode findMinNode(TreeNode node) {
while (node.left != null) {
node = node.left;
}
return node;
}
public void addToCart(int productId, int quantity) {
Product product = findProductById(root, productId);
if (product != null) {
int currentQuantity = cart.getOrDefault(productId, 0);
cart.put(productId, currentQuantity + quantity);
System.out.println(String.format("%d %s(s) added to cart.", quantity, product.getName()));
} else {
System.out.println("Product not found.");
}
}
private Product findProductById(TreeNode root, int productId) {
if (root == null) {
return null;
} else if (root.product.getId() == productId) {
return root.product;
} else if (productId < root.product.getId()) {
return findProductById(root.left, productId);
} else {
return findProductById(root.right, productId);
}
}
public void viewCart() {
if (cart.isEmpty()) {
System.out.println("Your cart is empty.");
} else {
System.out.println("Your cart:");
for (int productId : cart.keySet()) {
Product product = findProductById(root, productId);
int quantity = cart.get(productId);
System.out.println(String.format("%d x %s", quantity, product.getName()));
}
}
}
public void purchaseCart() {
if (cart.isEmpty()) {
System.out.println("Your cart is empty.");
} else {
double total = 0;
System.out.println("Purchase summary:");
for (int productId : cart.keySet()) {
Product product = findProductById(root, productId);
int quantity = cart.get(productId);
System.out.println(String.format("%d x %s ($%.2f each)", quantity, product.getName(), product.getPrice()));
total += product.getPrice() * quantity;
}
System.out.println(String.format("Total: $%.2f", total));
System.out.print("Confirm purchase? (Y/N) ");
Scanner scanner = new Scanner(System.in);
String choice = scanner.nextLine();
if (choice.equalsIgnoreCase("Y")) {
System.out.println("Thank you for your purchase!");
cart.clear();
} else {
System.out.println("Purchase cancelled.");
}
}
}
public void deleteFromCart(int productId, int quantity) {
Product product = findProductById(root, productId);
if (product != null) {
int currentQuantity = cart.getOrDefault(productId, 0);
if (currentQuantity > 0) {
if (currentQuantity - quantity <= 0) {
cart.remove(productId);
} else {
cart.put(productId, currentQuantity - quantity);
}
System.out.println(String.format("%d %s(s) removed from cart.", quantity, product.getName()));
} else {
System.out.println("No items of this product in your cart.");
}
} else {
System.out.println("Product not found.");
}
}
public void printCartBill() {
if (cart.isEmpty()) {
System.out.println("Your cart is empty.");
} else {
System.out.println("Your Bill:");
double total = 0;
System.out.println("PId\tQty\tProduct\t\tProductRate\tValue");
for (int productId : cart.keySet()) {
Product product = findProductById(root, productId);
int quantity = cart.get(productId);
double itemTotal = product.getPrice() * quantity;
System.out.println(String.format("%d\t%d\t%s\t\t$%.2f\t\t$%.2f", productId,quantity, product.getName(),product.getPrice(),itemTotal));
total += itemTotal;
}
System.out.println("-----------------------------------------------");
System.out.println(String.format("Total bill: \t\t\t\t$%.2f", total));
}
}
public static void main(String[] args) {
Scanner sc=new Scanner(System.in);
Inventory obj=new Inventory();
Product Shirt =new Product(1,"Shirt",399);
obj.addProduct(Shirt);
Product TShirt =new Product(2,"TShirt",499);
obj.addProduct(TShirt);
Product Jeans =new Product(3,"Jeans",999);
obj.addProduct(Jeans);
Product Shorts =new Product(4,"Shorts",299);
obj.addProduct(Shorts);
Product Hoodie=new Product(5,"Hoodie",1499);
obj.addProduct(Hoodie);
Product Saree =new Product(6,"Saree",399);
obj.addProduct(Saree);
Product Traditional =new Product(7,"Traditional",499);
obj.addProduct(Traditional);
Product CropTshirt =new Product(8,"CropTshirt",349);
obj.addProduct(CropTshirt);
Product WJeans =new Product(9,"WJeans",999);
obj.addProduct(WJeans);
Product WHoodie=new Product(10,"WHoodie",1399);
obj.addProduct(WHoodie);
Product Dresss=new Product(11,"Dress",1799);
obj.addProduct(Dresss);
Product Laptop = new Product(12, "Laptop", 79999);
obj.addProduct(Laptop);
Product Mobile = new Product(13, "Mobile", 49999);
obj.addProduct(Mobile);
Product Headphones = new Product(14, "Headphones", 2999);
obj.addProduct(Headphones);
Product Tab = new Product(15, "Tab", 39999);
obj.addProduct(Tab);
Product AirPods = new Product(16, "Airpods", 19999);
obj.addProduct(AirPods);
Product TV = new Product(17, "TV", 99999);
obj.addProduct(TV);
Product Fruit = new Product(18, "Fruit", 99);
obj.addProduct(Fruit);
Product Vegetable = new Product(19, "Vegetable", 69);
obj.addProduct(Vegetable);
Product Wafers = new Product(20, "Wafers", 29);
obj.addProduct(Wafers);
Product Chocolate = new Product(21, "Chocolate", 69);
obj.addProduct(Chocolate);
Product Icecream = new Product(22, "Icecream", 39);
obj.addProduct(Icecream);
Product Medicine = new Product(23, "Medicine", 99);
obj.addProduct(Medicine);
Product Lipstick = new Product(24, "Lipstick", 199);
obj.addProduct(Lipstick);
Product Foundation = new Product(25, "Foundation", 299);
obj.addProduct(Foundation);
Product kajol = new Product(26, "Kajol", 149);
obj.addProduct(kajol);
Product Eyeliner = new Product(27, "Eyeliner", 99);
obj.addProduct(Eyeliner);
Product Eyeshadow = new Product(28, "Eyeshadow", 199);
obj.addProduct(Eyeshadow);
Product Compact = new Product(29, "Compact", 249);
obj.addProduct(Compact);
Product Trolley = new Product(30, "Trolley-Bag", 2000);
obj.addProduct(Trolley);
Product handbag = new Product(31, "Hand-Bag", 399);
obj.addProduct(handbag);
Product Gym = new Product(32, "Gym/Shoulder/Duffle", 489);
obj.addProduct(Gym);
Product Backpack = new Product(33, "BackPack", 999);
obj.addProduct(Backpack);
Product wallet = new Product(34, "Wallet", 449);
obj.addProduct(wallet);
Product LaptopBag = new Product(35, "Laptop-Bag", 2199);
obj.addProduct(LaptopBag);
int n;
do {
System.out.println("1.FASHION \t 2.ELECTRONICS\t 3.GROCERY \t 4.BEAUTY PRODUCTS \t 5.BAGS & LUGGAGES");
System.out.println("Enter your choice");
n=sc.nextInt();
int s;
switch(n) {
case 1: System.out.println("1.MEN'S FASHION \t 2.WOMEN'S FASHION \t");
System.out.println("Enter your choice");
int m=sc.nextInt();
switch(m) {
case 1:
System.out.println(" 1.Shirt \n 2.Tshirts \n 3.Jeans \n 4.Shorts \n 5.Hoodie");
int y=sc.nextInt();
do {
System.out.println(" 1.ADD TO CART \t 2.REMOVE FROM CART 0.EXIT");
s=sc.nextInt();
switch(s) {
case 1 : System.out.println(" Quantity to add ?");
int q=sc.nextInt();
obj.addToCart(y, q);
obj.viewCart();
break;
case 2:
System.out.println(" Quantity to remove ?");
int q1=sc.nextInt();
obj.deleteFromCart(y, q1);
obj.viewCart();
break;
}
} while(s!=0);
break;
case 2:
System.out.println(" 1.Saree \n 2.Traditional \n 3.CropTshirt \n 4.WJeans \n 5.WHoodie \n 6.Dress");
int y1=sc.nextInt();
do {
System.out.println(" 1.ADD TO CART \t 2.REMOVE FROM CART 0.EXIT");
s=sc.nextInt();
switch(s) {
case 1 : System.out.println(" Quantity to add ?");
int q=sc.nextInt();
obj.addToCart((y1+5), q);
obj.viewCart();
break;
case 2:
System.out.println(" Quantity to remove ?");
int q1=sc.nextInt();
obj.deleteFromCart((y1+5), q1);
obj.viewCart();
break;
}
} while(s!=0);
}break;
case 2:
System.out.println(" 1.Laptop \n 2.Mobile \n 3.Headphones \n 4.Tab \n 5.Airpods \n 6.TV");
int y1=sc.nextInt();
do {
System.out.println(" 1.ADD TO CART \t 2.REMOVE FROM CART \t0.EXIT");
s=sc.nextInt();
switch(s) {
case 1 : System.out.println(" Quantity to add ?");
int q=sc.nextInt();
obj.addToCart((y1+11), q);
obj.viewCart();
break;
case 2:
System.out.println(" Quantity to remove ?");
int q1=sc.nextInt();
obj.deleteFromCart(y1+11, q1);
obj.viewCart();
break;}
}while(s!=0);
break;
case 3:
System.out.println(" 1.Fruit \n 2.Vegetable \n 3.Wafers \n 4.Chocolate \n 5.Icecream \n 6.Medicine");
int y2=sc.nextInt();
do {
System.out.println(" 1.ADD TO CART \t 2.REMOVE FROM CART \t 0.EXIT");
s=sc.nextInt();
switch(s) {
case 1 : System.out.println(" Quantity to add ?");
int q=sc.nextInt();
obj.addToCart((y2+17), q);
obj.viewCart();
break;
case 2:
System.out.println(" Quantity to remove ? ");
int q1=sc.nextInt();
obj.deleteFromCart(y2+17, q1);
obj.viewCart();
break;}
}while(s!=0);
break;
case 4:
System.out.println(" 1.Lipstick \n 2.Foundation \n 3.Kajol \n 4.EyeLiner \n 5.Eyeshadow \n 6.Compact");
int y3=sc.nextInt();
do {
System.out.println(" 1.ADD TO CART \t 2.REMOVE FROM CART \t 0.EXIT");
s=sc.nextInt();
switch(s) {
case 1 : System.out.println(" Quantity to add ? ");
int q=sc.nextInt();
obj.addToCart((y3+23), q);
obj.viewCart();
break;
case 2:
System.out.println(" Quantity to remove ?");
int q1=sc.nextInt();
obj.deleteFromCart(y3+23, q1);
obj.viewCart();
break;}
}while(s!=0);
break;
case 5:
System.out.println(" 1.Trolley-Bag \n 2.Hand-Bag \n 3.Gym/Shoulder/Duffle \n 4.Wallet \n 5.Laptop-Bag");
int y4=sc.nextInt();
do {
System.out.println(" 1.ADD TO CART \t 2.REMOVE FROM CART \t 0.EXIT");
s=sc.nextInt();
switch(s) {
case 1 : System.out.println(" Quantity to add ? ");
int q=sc.nextInt();
obj.addToCart((y4+29), q);
obj.viewCart();
break;
case 2:
System.out.println(" Quantity to remove ?");
int q1=sc.nextInt();
obj.deleteFromCart(y4+29, q1);
obj.viewCart();
break;
}
}
    while(s!=0);
break;
}
}while(n!=0);
obj.printCartBill();
}
}
