const winningKeys = [
  [0, 1, 2],
  [0, 4, 8],
  [0, 3, 6],
  [1, 4, 7],
  [2, 5, 8],
  [2, 4, 6],
  [3, 4, 5],
  [6, 7, 8]
];
let myBoard = [
  zero = {
    val: 0,
    player: null
  },
  one = {
    val: 1,
    player: null
  },
  two = {
    val: 2,
    player: null
  },
  three = {
    val: 3,
    player: null
  },
  four = {
    val: 4,
    player: null
  },
  five = {
    val: 5,
    player: null
  },
  six = {
    val: 6,
    player: null
  },
  seven = {
    val: 7,
    player: null
  },
  eight = {
    val: 8,
    player: null
  },
];
let human = "O";
let cpu = "X";
let count = 0;
const grid = document.querySelector(".grid-container");
const modal = document.querySelector(".modal");
const board = document.querySelectorAll(".board");
const button = document.querySelectorAll('button');
const display = document.querySelector("#my-result");
const score = document.querySelector('#score');
let checkTie = function() {
  let noMoves = myBoard.filter(pos => pos.player === null);
  if (noMoves.length === 0) {
    return true;
  }
}

function checkWin(who, board) {
  let arr = board.filter(pos => pos.player === who).map(pos => pos.val);
  let result;
  for (i = 0; i < winningKeys.length; i++) {
    if (result === true) {
      break;
    }
    result = winningKeys[i].every(function(val) {
      return arr.indexOf(val) !== -1;
    });
  };
  return result;
}

let movesLeft = function(board) {
  return board.filter(pos => pos.player === null).map(pos => pos.val);
}

let cpuAi = function(player, board) {
  let spots = movesLeft(board);
  if (checkWin(human, board)) {
    return {
      score: -10
    }
  } else if (checkWin(cpu, board)) {
    return {
      score: 10
    }
  } else if (checkTie()) {
    return {
      score: 0
    }
  }
  let moves = [];
  for (let i = 0; i < spots.length; i++) {
    let innerMove = {};
    innerMove.pos = board[spots[i]];
    board[spots[i]].player = player;
    if (player == cpu) {
      var result = cpuAi(human, board);
      innerMove.score = result.score;
    } else {
      var result = cpuAi(cpu, board);
      innerMove.score = result.score;
    }
    board[spots[i]].player = null;
    moves.push(innerMove);
  }
  var bestMove;
  if (player === cpu) {
    var bestScore = -10000;
    for (var i = 0; i < moves.length; i++) {
      if (moves[i].score > bestScore) {
        bestScore = moves[i].score;
        bestMove = i;
      }
    }
  } else {
    var bestScore = 10000;
    for (var i = 0; i < moves.length; i++) {
      if (moves[i].score < bestScore) {
        bestScore = moves[i].score;
        bestMove = i;
      }
    }
  }
  return moves[bestMove];
}

let humanPick = function() {
  let x = this.id.replace(/\D/g, "");
  if (myBoard[x].player === null) {
    myBoard[x].player = human;
    document.getElementById("pos-" + x).innerHTML = human;
    if (checkTie()) {
      display.innerHTML = "Tie Game";
      setTimeout(gameReset, 2000);
    } else {
      setTimeout(cpuPick, 1000);
      playerControl('n');
    }
  }
}

let cpuPick = function() {
  let y = cpuAi(cpu, myBoard);
  let pick = y.pos.val;
  myBoard[pick].player = cpu;
  document.getElementById("pos-" + pick).innerHTML = cpu;
  if (checkTie()) {
    display.innerHTML = "Tie Game";
    setTimeout(gameReset, 2000);
  } else if (checkWin(cpu, myBoard)) {
    display.innerHTML = "You Lose";
    updateScore();
    setTimeout(gameReset, 2000);
  } else {
    playerControl('y');
  }
}

let showBoard = function() {
  grid.style.display = "grid";
}

function startGame() {
  let pick = this.value;
  human = pick;
  if (pick === "X") {
    cpu = "O";
    playerControl('y');
  } else {
    setTimeout(cpuPick, 1500);
  }
  modal.style.display = "none";
  setTimeout(showBoard, 1000);
}

let playerControl = function(status) {
  if (status === "y") {
    for (let i = 0; i < board.length; i++) {
      board[i].addEventListener('click', humanPick, false);
    }
  } else {
    for (let i = 0; i < board.length; i++) {
      board[i].removeEventListener('click', humanPick, false);
    }
  }
}

let gameReset = function(){
  myBoard.forEach(x => x.player = null);
  board.forEach(x => x.innerHTML = "");
  display.innerHTML = "";
  if(cpu === "O"){
    cpu = "X";
    human = "O";
    playerControl('n');
    setTimeout(cpuPick, 1500);
  } else {
    cpu = "O";
    human = "X";
    playerControl('y');
  }
}

let updateScore = function(){
  count++;
  score.innerHTML = "0 - " + count;
}

for (let i = 0; i < button.length; i++) {
  button[i].addEventListener('click', startGame, false);
}