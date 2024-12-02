var arr := [1, 2, 3];      
print arr[1], arr[2];
print arr;      

arr[4] := 10;              
print arr[4];
print arr;      

var tup := {a := 5, b := "text", 7.5, func (x) => x*x};  
print tup;        

tup := tup + {c := true};  
print tup;               
