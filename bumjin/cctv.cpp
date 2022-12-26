#include <iostream>
#include <cstring>
#include <vector>
#define endl "\n"
#define MAX 12
using namespace std;

int N,M;
int MAP[MAX][MAX];
int MAP_2[MAX][MAX];
int cctv_number = 0;
int answer=99999;
vector<pair<pair<int, int>, pair<int, int>>> V;

bool Near(int x,int y){
    if(x<=0 || y<=0 || x>N || y>M)return false;
    return true;
}

void Up(int x,int y){
    int nx = x;
    int ny = y;
    while(1){
        if(MAP_2[nx][ny] == 6)break;
        if(Near(nx,ny) == false)break;
        if(MAP_2[nx][ny] == 0){
            MAP_2[nx][ny] = 7;
        }
        nx = nx-1;
    }
}

void Down(int x,int y){
    int nx=x;
    int ny=y;
    while(1){
        if(MAP_2[nx][ny] == 6)break;
        if(Near(nx,ny) == false)break;
        if(MAP_2[nx][ny] == 0){
            MAP_2[nx][ny] = 7;
        }
        nx = nx+1;
    }
}

void Left(int x,int y){
    int nx = x;
    int ny = y;
    while(1){
        if(MAP_2[nx][ny] == 6)break;
        if(Near(nx,ny) == false)break;
        if(MAP_2[nx][ny] == 0){
            MAP_2[nx][ny] = 7;
        }
        ny = ny-1;
    }
}

void Right(int x,int y){
    int nx = x;
    int ny = y;
    while(1){
        if(MAP_2[nx][ny] == 6)break;
        if(Near(nx,ny) == false)break;
        if(MAP_2[nx][ny] == 0){
            MAP_2[nx][ny] = 7;
        }
        ny = ny + 1;
    }   
}


void Check_CCTV_Area(int cctv ,int dir,int x,int y){
    if(cctv == 1){
        if(dir == 1){
            Up(x,y);
        }else if(dir == 2){
            Down(x,y);
        }else if(dir == 3){
            Left(x,y);
        }else if(dir == 4){
            Right(x,y);
        }
    }
    else if(cctv == 2){
        if(dir == 1 || dir == 2){
            Up(x,y); 
            Down(x,y);
        }else if(dir == 3 ||dir == 4){
            Left(x,y);
            Right(x,y);
        }
    }
    else if(cctv == 3){
        if(dir == 1){//위
            Up(x,y);
            Right(x,y);
        }else if(dir == 2){//아래
            Down(x,y);
            Left(x,y);
        }else if(dir == 3){//왼
            Up(x,y);
            Left(x,y);
        }else if(dir == 4){//오른쪽
            Down(x,y);
            Right(x,y);
        }
    }
    else if(cctv == 4){//4방향 모두 업데이트
         if(dir == 1){//위
            Up(x,y);
            Left(x,y);
            Right(x,y);
        }else if(dir == 2){//아래
            Down(x,y);
            Left(x,y);
            Right(x,y);
        }else if(dir == 3){//왼
            Down(x,y);
            Left(x,y);
            Up(x,y);
        }else if(dir == 4){//오른쪽
            Down(x,y);
            Right(x,y);
            Up(x,y);
        }
    }
    else if(cctv == 5){//4방향 모두 업데이트
        Up(x,y); 
        Down(x,y);
        Left(x,y);
        Right(x,y);
    }
}

void Copy(){
     for (int i = 1; i <= N; i++)
    {
        for (int j = 1; j <= M; j++)
        {
            MAP_2[i][j]=MAP[i][j];
        }
    }
}

int GetBlindSpotNumber(){
    int blind_spot=0;
    for(int i=1; i<= N;i++)
       for(int j = 1;j <= M; j++){
        if(MAP_2[i][j] == 0){
            blind_spot++;
        }
    }
    return blind_spot;
}

void GetBlindSpot(){
    Copy();
    for(int i = 1;i <= cctv_number; i++){
        Check_CCTV_Area(V[i].second.first,V[i].second.second,V[i].first.first,V[i].first.second);
    }
} 

void Input()
{
    V.push_back(make_pair(make_pair(-1, -1), make_pair(-1, -1)));
    cin >> N;
    cin >> M;
    for (int i = 1; i <= N; i++)
    {
        for (int j = 1; j <= M; j++)
        {
            cin >> MAP[i][j];
            if(1 <= MAP[i][j] && MAP[i][j] <= 5){
                cctv_number++;
                V.push_back(make_pair(make_pair(i, j), make_pair(MAP[i][j], -1)));
            }
        }
    }
}
void show(){
    for(int i=1; i<=N;i++){
            for(int j=1; j<=M;j++){
                printf("%d",MAP_2[i][j]);
            } 
        printf("\n");
        }
        printf("\n");   
}
void Permutation(int cnt){
    if(cnt == cctv_number+1){
        GetBlindSpot();
       // show();
        answer=min(answer,GetBlindSpotNumber());
        return ;
    }
    V[cnt].second.second = 1; 
    Permutation(cnt+1);
    V[cnt].second.second = 2;
    Permutation(cnt+1);
    V[cnt].second.second = 3;
    Permutation(cnt+1);
    V[cnt].second.second = 4;
    Permutation(cnt+1);
}

int main(){
    Input();
    Permutation(1);
    printf("%d\n",answer);
return 0;    
}
