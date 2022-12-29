#include <iostream>
#include <cstring>
#include <vector>
#define endl "\n"
#define MAX 102
#include <queue>
using namespace std;
int n;
int m;
int board[MAX][MAX];
int dist[MAX][MAX];
int visited[MAX][MAX];
vector<pair<int, int>> chicken;
int result[14]={0,}; 
int dx[4]={1,-1,0,0};
int dy[4]={0,0,1,-1};
int ans = 999999;

 void Input(){
  cin >> n;
  cin >> m;
  for(int i =1 ; i <= n ;i++)
    for(int j =1 ; j <= n ;j++){
      cin >>  board[i][j];
      if(board[i][j] == 2){
        chicken.push_back(make_pair(i,j));
        board[i][j] = 0;//빈집 처리
      }
    }
}

void ResetDist(){
    for(int i=0;i<MAX;i++){
       for(int j=0;j<MAX;j++){
        dist[i][j]=9999;
        } 
    }
}

void ResetVisit(){
    for(int i=0;i<MAX;i++){
       for(int j=0;j<MAX;j++){
        visited[i][j]=0;
        } 
    }
}

bool IsNear(int nx,int ny){
  if(nx<1||ny<1||nx>n||ny>n)return false;
  return true;
}

void Bfs(int x, int y){
  queue<pair<int,int>> q;
  dist[x][y] = 0;
  q.push(make_pair(x,y));
  visited[x][y] = true;
  while(!q.empty()){
    int x=q.front().first;
    int y=q.front().second;
    q.pop();
    for(int i=0;i<4;i++){
      int nx=x+dx[i];
      int ny=y+dy[i];
      if(IsNear(nx,ny) == false)continue;
      if(visited[nx][ny]==1)continue;
        visited[nx][ny]=1;
        dist[nx][ny]=min(dist[nx][ny],dist[x][y]+1);
        q.push(make_pair(nx,ny));
      }
  };
  return ;
}

void GetMin(){
  int chicken_dist = 0;
  for(int i =1 ; i <= n ;i++)
    for(int j =1 ; j <= n ;j++){
      if(board[i][j]==1){
        chicken_dist = chicken_dist + dist[i][j];
      }
    }
    ans = min(ans, chicken_dist); 
}

void Print(){
 for(int i =1 ; i <= n ;i++){
    for(int j =1 ; j <= n ;j++){
      printf("%d ",dist[i][j]);
    }
    printf("\n");
 }
}

void  Combination(int cnt,int start){
if(cnt == m){
  /*do something*/
  ResetDist();
  for(int num = 0;num < m; num++){//조합으로 뽑은  좌표를 모두 bfs 처리
    int idx = result[num];
    int x = chicken[idx].first; 
    int y = chicken[idx].second; 
    Bfs(x,y);
    ResetVisit();
  }
 // Print();
  GetMin();
  return;
}
for(int i = start;i < chicken.size(); i++){
    result[cnt] = i;
    Combination(cnt+1,i+1);
  }
}

int main(){
  Input();
  Combination(0,0);
  printf("%d",ans);
return 0;    
}
