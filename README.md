# PizzaNeck
:pizza: 거북목 예방 어플 [캡스톤디자인]

> 본 프로젝트는 현대인의 건강을 위협하는 `거북목증후군` 및 `일자목증후군`을 예방하기 위하여 
잘못된 자세를 취했을 시 실시간으로 이를 경고하여 올바른 자세로 교정해주는 안드로이드 애플리케이션을 제작하는 것을 목적으로 한다.

</br>

|핵심기능                       |구현                         |
|-------------------------------|-----------------------------|
|자세 실시간 감지               |딥러닝                        |
|통계자료                       |MySQL                         |
|데이터 수집                    |웹 크롤러                     |

</br>

- 딥러닝 모델
> 수집한 이미지를 128 by 128로 사이즈를 변환한 후 cnn(컨볼루션(Convolution)망)과 
Maxpooling을 반복하여 학습 시키고, (Flatten과 Dense함수를 이용) 
1차원 배열로 변환하여 결과를 예측할 수 있는 모델을 구축
<img src="https://user-images.githubusercontent.com/55950992/194070592-c4df9253-5dda-4b61-8c69-4ae845d8fe9f.png" width="600" height="150" />


- 딥러닝 자세 감지 학습 87% 의 정확도
<img src="https://user-images.githubusercontent.com/55950992/194069176-3e0c16ad-a6c9-478a-8579-98dac8914476.png" width="250" height="50" />
<img src="https://user-images.githubusercontent.com/55950992/194069363-f8750f6d-3e77-488c-a418-60732a418f6c.png" width="370" height="250" />

- UI / UX 디자인
<img src="https://user-images.githubusercontent.com/55950992/194067948-8cf1d3f9-9adf-4bf9-ac02-850d041c38ea.png" width="600" height="400" />

