# PizzaNeck
:pizza: 거북목 예방 어플 [캡스톤디자인]
![image](https://user-images.githubusercontent.com/55950992/232935388-8c63bbab-c57c-4bb8-a716-d3c2f7470138.png)
![image](https://user-images.githubusercontent.com/55950992/232935467-6e9f832e-fcbc-4013-98d7-2d3fde94a9c6.png)

> 본 프로젝트는 현대인의 건강을 위협하는 `거북목증후군` 및 `일자목증후군`을 예방하기 위하여 
잘못된 자세를 취했을 시 실시간으로 이를 경고하여 올바른 자세로 교정해주는 안드로이드 애플리케이션을 제작하는 것을 목적으로 한다.

</br>

✔️**구현 사항**

- AI를 활용한 잘못된 자세 실시간 감지
- 핸드폰 알림을 통한 경고
- 스트레칭 정보 제공을 통한 예방효과
- 통계자료를 통한 각종 데이터 시각화
- 테마 색상, 알림 설정

✔️**담당 역할**

- Mobile, 딥러닝 모델 학습
- UI/UX 디자인
- 모바일 화면 구성
- 딥러닝 모델 학습 데이터 수집

✔️**기술 스택**
</br>
</br>
<img src="https://img.shields.io/badge/androidstudio-3DDC84?style=for-the-badge&logo=androidstudio&logoColor=white"> 
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
<img src="https://img.shields.io/badge/python-3776AB?style=for-the-badge&logo=python&logoColor=white">
<img src="https://img.shields.io/badge/sqlite-003B57?style=for-the-badge&logo=sqlite&logoColor=white"> 
<img src="https://img.shields.io/badge/github-F05032?style=for-the-badge&logo=github&logoColor=white">
<img src="https://img.shields.io/badge/adobexd-FF61F6?style=for-the-badge&logo=adobexd&logoColor=white">

</br>

- 딥러닝 모델
![그림1](https://user-images.githubusercontent.com/55950992/232935627-10af4e85-61e0-48e0-a5cd-697f29b8e923.png)
> 수집한 이미지를 128 by 128로 사이즈를 변환한 후 cnn(컨볼루션(Convolution)망)과 
Maxpooling을 반복하여 학습 시키고, (Flatten과 Dense함수를 이용) 
1차원 배열로 변환하여 결과를 예측할 수 있는 모델을 구축
<img src="https://user-images.githubusercontent.com/55950992/194070592-c4df9253-5dda-4b61-8c69-4ae845d8fe9f.png" width="600" height="150" />


- 딥러닝 자세 감지 학습 87% 의 정확도
<img src="https://user-images.githubusercontent.com/55950992/194069176-3e0c16ad-a6c9-478a-8579-98dac8914476.png" width="250" height="50" />
<img src="https://user-images.githubusercontent.com/55950992/194069363-f8750f6d-3e77-488c-a418-60732a418f6c.png" width="370" height="250" />

- UI / UX 디자인
<img src="https://user-images.githubusercontent.com/55950992/194067948-8cf1d3f9-9adf-4bf9-ac02-850d041c38ea.png" width="600" height="400" />


- 구현결과

<메인페이지>
![image](https://user-images.githubusercontent.com/55950992/232935810-824f5dfd-3403-41d9-aeb3-d7a3e17e6ae5.png)
<실시간 자세 감지>
![image](https://user-images.githubusercontent.com/55950992/232935835-289c45ff-5d2e-4b04-aa59-878d823011e3.png)
<스트레칭 정보>
![image](https://user-images.githubusercontent.com/55950992/232935850-6245066b-31f8-42d6-853a-69f3dc08e4ec.png)
<통계>
![image](https://user-images.githubusercontent.com/55950992/232935872-a302efc8-9827-4a15-b385-f43ed7fb554c.png)
<설정>
![image](https://user-images.githubusercontent.com/55950992/232935939-f542edda-f1c8-47ba-a963-069a5fcffba0.png)
<다크모드>
![image](https://user-images.githubusercontent.com/55950992/232935964-2efdd2a1-53d0-4785-9940-3458dcdc72bb.png)


