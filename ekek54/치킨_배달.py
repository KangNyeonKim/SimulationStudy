import sys

N, M = map(int, sys.stdin.readline().split());
board = [list(map(int, sys.stdin.readline().split())) for _ in range(N)]
home_list = []
bbq_list = []
selected_bbq = []

# 집과 치킨집 위치 파악
for i in range(N):
  for j in range(N):
    if board[i][j] == 1:
      home_list.append((i, j))
    elif board[i][j] == 2:
      bbq_list.append((i, j))

home_num = len(home_list)
bbq_num = len(bbq_list)
candidate = []


# 두 좌표간의 거리
# 치킨거리 구할 때 사용
def distance(a, b):
  return abs(a[0] - b[0]) + abs(a[1] - b[1])


# 두 좌표간의 크기 비교
# a가 작을경우 true 반환
# 치킨집 좌표간의 비교에 사용
def compare(a, b):
  return a[0] < b[0] or (a[0] == b[0] and a[1] <= b[1])


# 선택된 치킨집 조합의 치킨거리 계산
def check(home_list, bbq_list):
  arr = []
  for i in range(home_num):
    tmp = 200  # inf 치킨거리
    for j in range(M):
      tmp = min(tmp, distance(home_list[i], bbq_list[j]))
    arr.append(tmp)
  return sum(arr)


# 치킨집 M개 조합 구하기
def dfs(cnt):
  if cnt == M:
    candidate.append(check(home_list, selected_bbq))
    return

  for i in range(bbq_num):
    if selected_bbq and compare(bbq_list[i], selected_bbq[-1]):
      continue
    selected_bbq.append(bbq_list[i])
    dfs(cnt + 1)
    selected_bbq.pop()


dfs(0)
print(min(candidate))
