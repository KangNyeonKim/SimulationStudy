#include <iostream>
#include <cstring>
#include <vector>
#include <queue>
#define endl "\n"
#define MAX 14
using namespace std;
string board[MAX];
int visited[MAX][MAX];
int dx[4]={1,-1,0,0};
int dy[4]={0,0,1,-1};
bool flag = true;
int r = 12;
int c = 6;
 void Input(){
  for(int i = 0 ; i < r ;i++)
     		cin >> board[i];
}

bool IsNear(int nx,int ny){
  if(nx < 0||ny < 0||nx >= r||ny >= c)return false;
  return true;
}

void Bfs(int x, int y){
  char init_char = board[x][y];
  int cnt = 0;
  queue<pair<int,int>> q;
  queue<pair<int,int>> q_pop;
  q.push(make_pair(x,y));
  q_pop.push(make_pair(x,y));
  visited[x][y] = true;
  cnt ++;
  while(!q.empty()){
    int x=q.front().first;
    int y=q.front().second;
    q.pop();
    for(int i = 0 ; i < 4 ; i++){
      int nx=x+dx[i];
      int ny=y+dy[i];
      if(IsNear(nx,ny) == false)continue;
      if(visited[nx][ny]==1)continue;
      if(board[nx][ny] != init_char)continue;
        visited[nx][ny]=1;
        cnt ++;
        q.push(make_pair(nx,ny));
        q_pop.push(make_pair(nx,ny));
      }
  }
  if(cnt >= 4){
    flag = true;
    while(!q_pop.empty()){
      int x=q_pop.front().first;
      int y=q_pop.front().second;
      board[x][y] = '.';
      q_pop.pop();
    }
  }
}

void Print(){
    printf("start\n");
  for(int i = 0; i < r ;i++){
    for(int j = 0 ; j < c ;j++){
      printf("%c ",board[i][j]);
    }
    printf("\n");
  }
}

void Pop(){
  for(int i = 0 ; i < r ;i++){
      for(int j = 0 ; j < c ;j++){
        if(board[i][j]  != '.' && visited[i][j] == false){
          Bfs(i,j);
      }
    }
}
}

void Down(){
  for(int j = 0 ; j < c ;j++){
    vector <char> temp;
    for(int i = r-1 ; i >= 0  ;i--){
      if(board[i][j] != '.')temp.push_back(board[i][j]);
    }
    while(temp.size() <= 12){
      temp.push_back('.');
    }
    for(int t = 0 ; t <12; t++)board[11 - t][j] = temp[t];
  }
}

void VisitReset(){
   for(int i =0; i < r ;i++)
    for(int j = 0 ; j < c ;j++)visited[i][j] = 0;
}

int main(){
  Input();
  int answer = 0;
  while(1){
    flag = false;
    Pop();
    Down();
   // Print();
    VisitReset();
    if(flag == true)answer ++;
    else if(flag == false) break;
  }
  printf("%d", answer);
return 0;    
}
