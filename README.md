# RACHAPUNG Phone Mod (Fabric 26.3)

ม็อดมือถือ **RACHAPUNG** สำหรับ Minecraft (Fabric) มาพร้อมหน้าจอ UI แอป **YouTube Shorts** และเครื่องมือช่วยเล่นในเกม

## ฟีเจอร์ในมือถือ RACHAPUNG
- **ไอเทมมือถือ RACHAPUNG** (อยู่ในแท็บ Tools ของโหมด Creative หรือใช้คำสั่ง `/give @s rachapungphone:rachapung_phone`)
- **แอป YouTube Shorts**: หน้าจอฟีดแนวตั้ง 9:16 เลื่อนดูคลิปขึ้น/ลง (`▲`/`▼`), กดถูกใจ (`♡`/`♥`), กดหยุด/เล่นต่อ, มีแถบเวลาเล่นคลิป และปุ่ม **เปิดดูคลิปจริง** เพื่อเปิด YouTube Shorts ในเบราว์เซอร์ทันที
- **เช็กพิกัด GPS** และ **แชร์พิกัดลงแชท**

## วิธีแปลงเป็นไฟล์ `.jar` เพื่อลงเกม
1. แตกไฟล์ ZIP นี้ แล้วเปิดโฟลเดอร์ด้วย **IntelliJ IDEA** (แนะนำ JDK 21)
2. ตรวจสอบเวอร์ชันในไฟล์ `gradle.properties` (`minecraft_version`, `yarn_mappings`, `loader_version`, `fabric_version`) ให้ตรงกับเวอร์ชัน Minecraft / Fabric ที่คุณติดตั้งไว้
3. รันคำสั่งใน Terminal:
   - Windows: `gradlew build`
   - macOS / Linux: `./gradlew build`
4. นำไฟล์ `.jar` ที่ได้จากโฟลเดอร์ `build/libs/` ไปวางในโฟลเดอร์ `.minecraft/mods/`
