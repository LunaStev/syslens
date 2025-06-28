use sysinfo::{System};
use std::ffi::{CString};
use std::os::raw::c_char;

#[no_mangle]
pub extern "C" fn get_sys_info() -> *const c_char {
    let mut sys = System::new_all();
    sys.refresh_all();

    let cpu = sys.global_cpu_usage();
    let total_memory = sys.total_memory();
    let used_memory = sys.used_memory();

    let result = format!(
        "CPU Usage: {:.2}% | Memory: {}/{} MB",
        cpu,
        used_memory / 1024,
        total_memory / 1024
    );

    CString::new(result).unwrap().into_raw()
}
