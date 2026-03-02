public class ClassroomController {
    private final DeviceRegistry reg;

    public ClassroomController(DeviceRegistry reg) { this.reg = reg; }

    public void startClass() {
        PowerControllable pjPower = reg.getFirst(Projector.class);
        InputConnectable pjInput = reg.getFirst(Projector.class);
        pjPower.powerOn();
        pjInput.connectInput("HDMI-1");

        BrightnessControllable lights = reg.getFirst(LightsPanel.class);
        lights.setBrightness(60);

        TemperatureControllable ac = reg.getFirst(AirConditioner.class);
        ac.setTemperatureC(24);

        AttendanceScannable scan = reg.getFirst(AttendanceScanner.class);
        System.out.println("Attendance scanned: present=" + scan.scanAttendance());
    }

    public void endClass() {
        System.out.println("Shutdown sequence:");
        reg.getFirst(Projector.class).powerOff();
        reg.getFirst(LightsPanel.class).powerOff();
        reg.getFirst(AirConditioner.class).powerOff();
    }
}
