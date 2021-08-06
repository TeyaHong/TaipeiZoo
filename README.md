# 台北市立動物園DEMO APP
簡單介紹台北市立動物園

#### 此專案使用的架構:
MVVM架構

#### 此專案使用的Library:
1. Glide: https://github.com/bumptech/glide
2. Retrofit: https://github.com/square/retrofit
3. OkHttp3: https://github.com/square/okhttp

#### 流程介紹
1. **館區簡介** <br/>
透過API取得園區內所有館區資料並將館區簡介以列表方式呈現，點擊後則進入館區詳細資料
2. **館區詳細資料** <br/>
除顯示館區詳細資料外，透過API取得分佈於該館區的植物資料並將植物簡介以列表方式呈現，點擊植物簡介後則進入該植物詳細資料
3. **館區域內植物詳細資料** <br/>
顯示植物詳細資料