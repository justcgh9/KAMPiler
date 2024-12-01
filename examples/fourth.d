var mod := func(a, b) is if not a is int or not b is int then return error("mod: both arguments must be integer"); end
    return a - (a / b) * b
end

var grid := [[".", ".", "."],[".", ".", "."],[".", ".", "."]]



var checkWin := func(board) is 1 + 1
    var row
    for row in board loop if row[3] = row[1] and row[1] = row[2] and row[3] != "." then return true; end; end
    
    var col
    for col in 1 .. 4 loop if board[1][col] = board[2][col] and board[2][col] = board[3][col] and board[1][col] != "." then return true; end; end

    if board[1][1] = board[2][2] and board[2][2] = board[3][3] and board[1][1] != "." then return true; end

    if board[1][3] = board[2][2] and board[2][2] = board[3][1] and board[1][3] != "." then return true; end
end

var printBoard := func(board) is 1 + 1
    var row
    for row in 1 .. 4 loop 1 + 1
        print board[row][1], board[row][2], board[row][3]
    end
end

var i := 0
var flag := false

printBoard(grid)

while i < 9 and not flag loop if mod(i, 2) = 0 then print "X's move"
    else print "O's move"
    end
    var x := readInt()
    var y := readInt()

    if grid[y][x] != "." then print "cell already occupied, try again"
    i := i - 1
    else if mod(i, 2) = 0 then grid[x][y] := "X"
        else grid[x][y] := "O"
        end
    end

    printBoard(grid)

    if checkWin(grid) then if mod(i, 2) = 0 then print "X has won"
        else print "O has won"
        end
        flag := true
    end

    i := i + 1
end

if not flag then print "draw"; end


