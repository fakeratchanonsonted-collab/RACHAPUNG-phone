package com.rachapung.phonemod.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class RachapungPhoneScreen extends Screen {
    private enum AppMode {
        HOME,
        YOUTUBE_SHORTS
    }

    private final int phoneWidth = 160;
    private final int phoneHeight = 250;

    private AppMode currentMode = AppMode.HOME;
    private final List<ShortsVideo> shortsFeed = new ArrayList<>();
    private int currentShortIndex = 0;
    private boolean isPlaying = true;
    private float playProgress = 0.0f;

    public RachapungPhoneScreen() {
        super(Text.literal("RACHAPUNG OS"));
        initShortsFeed();
    }

    private void initShortsFeed() {
        shortsFeed.clear();
        shortsFeed.add(new ShortsVideo(
                "วิธีสร้างบ้านสวยๆ ใน 60 วิ!",
                "@RACHAPUNG_MC",
                "https://www.youtube.com/shorts",
                0xFFCC181E,
                0xFF28080A,
                12400
        ));
        shortsFeed.add(new ShortsVideo(
                "10 เทคนิคขุดเพชรไวสุดในเซิร์ฟ",
                "@DiamondHunterTH",
                "https://www.youtube.com/shorts",
                0xFF0088AA,
                0xFF05202E,
                8950
        ));
        shortsFeed.add(new ShortsVideo(
                "รวมช็อตฮา Creeper ระเบิดบ้าน!",
                "@CreeperBoomTH",
                "https://www.youtube.com/shorts",
                0xFF2E8B57,
                0xFF0B2918,
                34200
        ));
        shortsFeed.add(new ShortsVideo(
                "Speedrun จบเกม Minecraft ไวจัด",
                "@SpeedRunPro",
                "https://www.youtube.com/shorts",
                0xFF6A1B9A,
                0xFF1F0830,
                51800
        ));
    }

    @Override
    protected void init() {
        rebuildWidgets();
    }

    private void rebuildWidgets() {
        this.clearChildren();
        int left = (this.width - phoneWidth) / 2;
        int top = (this.height - phoneHeight) / 2;

        if (currentMode == AppMode.HOME) {
            // แอป YouTube Shorts
            this.addDrawableChild(ButtonWidget.builder(Text.literal("§c▶ §fYouTube Shorts"), btn -> {
                currentMode = AppMode.YOUTUBE_SHORTS;
                playProgress = 0.0f;
                isPlaying = true;
                rebuildWidgets();
            }).dimensions(left + 15, top + 58, 130, 24).build());

            // แอปเช็กพิกัด GPS
            this.addDrawableChild(ButtonWidget.builder(Text.literal("§a📍 เช็กพิกัด GPS"), btn -> {
                if (this.client != null && this.client.player != null) {
                    int x = (int) this.client.player.getX();
                    int y = (int) this.client.player.getY();
                    int z = (int) this.client.player.getZ();
                    this.client.player.sendMessage(
                            Text.literal("§b[RACHAPUNG Phone] §fพิกัดของคุณ: §eX=" + x + " Y=" + y + " Z=" + z),
                            false
                    );
                }
            }).dimensions(left + 15, top + 90, 130, 22).build());

            // แอปส่งข้อความด่วน
            this.addDrawableChild(ButtonWidget.builder(Text.literal("§e💬 แชร์พิกัดลงแชท"), btn -> {
                if (this.client != null && this.client.player != null) {
                    int x = (int) this.client.player.getX();
                    int y = (int) this.client.player.getY();
                    int z = (int) this.client.player.getZ();
                    this.client.player.networkHandler.sendChatMessage(
                            "[RACHAPUNG Phone] ฉันอยู่ที่ X=" + x + " Y=" + y + " Z=" + z
                    );
                    this.close();
                }
            }).dimensions(left + 15, top + 118, 130, 22).build());

            // ปุ่มปิดหน้าจอ
            this.addDrawableChild(ButtonWidget.builder(Text.literal("⭕ ปิดเครื่อง"), btn -> this.close())
                    .dimensions(left + 40, top + phoneHeight - 30, 80, 20).build());

        } else if (currentMode == AppMode.YOUTUBE_SHORTS) {
            ShortsVideo current = shortsFeed.get(currentShortIndex);

            // ปุ่ม Play / Pause
            this.addDrawableChild(ButtonWidget.builder(Text.literal(isPlaying ? "⏸" : "▶"), btn -> {
                isPlaying = !isPlaying;
                rebuildWidgets();
            }).dimensions(left + 14, top + 155, 24, 20).build());

            // ปุ่มปัดขึ้น (คลิปก่อนหน้า)
            this.addDrawableChild(ButtonWidget.builder(Text.literal("▲"), btn -> {
                currentShortIndex = (currentShortIndex - 1 + shortsFeed.size()) % shortsFeed.size();
                playProgress = 0.0f;
                rebuildWidgets();
            }).dimensions(left + phoneWidth - 36, top + 72, 22, 20).build());

            // ปุ่มปัดลง (คลิปถัดไป)
            this.addDrawableChild(ButtonWidget.builder(Text.literal("▼"), btn -> {
                currentShortIndex = (currentShortIndex + 1) % shortsFeed.size();
                playProgress = 0.0f;
                rebuildWidgets();
            }).dimensions(left + phoneWidth - 36, top + 96, 22, 20).build());

            // ปุ่มถูกใจ (Like)
            String likeLabel = current.liked ? "§c♥" : "♡";
            this.addDrawableChild(ButtonWidget.builder(Text.literal(likeLabel), btn -> {
                current.liked = !current.liked;
                current.likes += current.liked ? 1 : -1;
                rebuildWidgets();
            }).dimensions(left + phoneWidth - 36, top + 124, 22, 20).build());

            // ปุ่มเปิดดู YouTube Shorts จริงในเบราว์เซอร์
            this.addDrawableChild(ButtonWidget.builder(Text.literal("§c🌐 เปิดดูคลิปจริง"), btn -> {
                try {
                    Util.getOperatingSystem().open(URI.create(current.url));
                } catch (Exception ignored) {
                }
            }).dimensions(left + 42, top + 155, 104, 20).build());

            // ปุ่มกลับหน้า Home
            this.addDrawableChild(ButtonWidget.builder(Text.literal("⌂ หน้าหลัก"), btn -> {
                currentMode = AppMode.HOME;
                rebuildWidgets();
            }).dimensions(left + 40, top + phoneHeight - 30, 80, 20).build());
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);

        int left = (this.width - phoneWidth) / 2;
        int top = (this.height - phoneHeight) / 2;

        // ขอบตัวเครื่อง RACHAPUNG (สีดำ-ขอบทองแดง/แดง)
        context.fill(left - 2, top - 2, left + phoneWidth + 2, top + phoneHeight + 2, 0xFFE53935);
        context.fill(left, top, left + phoneWidth, top + phoneHeight, 0xFF141414);

        // รอยบากกล้องด้านบน (Notch)
        context.fill(left + 58, top + 4, left + 102, top + 10, 0xFF000000);

        // พื้นที่หน้าจอด้านใน
        int screenLeft = left + 8;
        int screenTop = top + 14;
        int screenRight = left + phoneWidth - 8;
        int screenBottom = top + phoneHeight - 34;

        if (currentMode == AppMode.HOME) {
            context.fillGradient(screenLeft, screenTop, screenRight, screenBottom, 0xFF1B2838, 0xFF0E141B);
            context.drawCenteredTextWithShadow(this.textRenderer, "§6§lRACHAPUNG", this.width / 2, top + 24, 0xFFFFFF);
            context.drawCenteredTextWithShadow(this.textRenderer, "§75G  100% 🔋", this.width / 2, top + 38, 0xAAAAAA);
        } else if (currentMode == AppMode.YOUTUBE_SHORTS) {
            ShortsVideo current = shortsFeed.get(currentShortIndex);

            // พื้นหลังวิดีโอแนวตั้ง 9:16
            context.fillGradient(screenLeft, screenTop, screenRight, screenBottom, current.themeColorTop, current.themeColorBottom);

            // แถบหัวข้อ Shorts
            context.drawTextWithShadow(this.textRenderer, "§c▶ §f§lShorts", screenLeft + 6, screenTop + 6, 0xFFFFFF);
            context.drawTextWithShadow(this.textRenderer, "§7" + (currentShortIndex + 1) + "/" + shortsFeed.size(), screenRight - 24, screenTop + 6, 0xCCCCCC);

            // สัญลักษณ์วิดีโอกำลังเล่นตรงกลางจอ
            String playIcon = isPlaying ? "♪ กำลังเล่นคลิป..." : "⏸ หยุดชั่วคราว";
            context.drawCenteredTextWithShadow(this.textRenderer, playIcon, this.width / 2 - 8, top + 100, 0xFFFFFF);

            // จำนวน Like ใต้ปุ่มหัวใจ
            context.drawCenteredTextWithShadow(this.textRenderer, "§f" + formatLikes(current.likes), left + phoneWidth - 25, top + 146, 0xFFFFFF);

            // ชื่อช่องและชื่อคลิปด้านล่าง
            context.drawTextWithShadow(this.textRenderer, "§e" + current.channel, screenLeft + 6, screenBottom - 34, 0xFFFFFF);
            String trimmedTitle = current.title.length() > 22 ? current.title.substring(0, 22) + "..." : current.title;
            context.drawTextWithShadow(this.textRenderer, "§f" + trimmedTitle, screenLeft + 6, screenBottom - 22, 0xFFFFFF);

            // แถบเวลาเล่นคลิป (Progress bar)
            if (isPlaying) {
                playProgress += delta * 0.008f;
                if (playProgress >= 1.0f) {
                    playProgress = 0.0f;
                }
            }
            int barWidth = (screenRight - screenLeft) - 8;
            int filledWidth = (int) (barWidth * playProgress);
            context.fill(screenLeft + 4, screenBottom - 6, screenRight - 4, screenBottom - 3, 0x66FFFFFF);
            context.fill(screenLeft + 4, screenBottom - 6, screenLeft + 4 + filledWidth, screenBottom - 3, 0xFFFF0000);
        }

        super.render(context, mouseX, mouseY, delta);
    }

    private String formatLikes(int likes) {
        if (likes >= 1000) {
            return String.format("%.1fK", likes / 1000.0);
        }
        return String.valueOf(likes);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
