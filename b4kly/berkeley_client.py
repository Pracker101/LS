import socket
import time
import random

HOST = "localhost"
PORT = 5000

def get_random_time():
    """Simulates a local clock with a random time."""
    return random.randint(50, 100)  # Simulated clock time (50-100)

def main():
    local_time = get_random_time()
    print(f"Client Clock Time: {local_time}")

    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as client_socket:
        client_socket.connect((HOST, PORT))

        # Send local time to master
        client_socket.sendall(str(local_time).encode())

        # Receive adjustment from master
        adjustment = int(client_socket.recv(1024).decode())
        print(f"Received adjustment: {adjustment}")

        # Adjust local time
        local_time += adjustment
        print(f"Updated Client Clock: {local_time}")

if __name__ == "__main__":
    main()
