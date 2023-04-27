int n = 11;
float w;
float h;
CatGame cg;
int catRow;
int catCol;

void setup() {
  size(300,300);
  cg = new CatGame(n);
  surface.setResizable(true);
}

void draw() {
  int[] catLoc = cg.getCatTile();
  catRow = catLoc[0];
  catCol = catLoc[1];
  
  if (cg.catIsTrapped()) {
    background(0, 255, 0);
    return;
  } else if (cg.catHasEscaped()) {
    background(255,0,0);
    return;
  }
  
  w = width / (n + 0.5);
  h = (float) height / n;
  
  for (int row = 0; row < n; row++) {
    for (int col = 0; col < n; col++) {
     if (cg.marked(row, col)) fill(0);
     else if (catCol == col && catRow == row) fill(0,0,255);
     else fill(255,255,255);
     rect(w * col + (row % 2) * w / 2, h * row, w, h);
    }
  }
}

void mouseClicked() {
  int row = (int) Math.floor((mouseY / h));
  int col = (int) Math.floor((mouseX - ((row % 2) * w/2)) / w);
  
  if (row < 0 || row > n || col < 0 || col > n) return;
  
  cg.markTile(row, col);
}
