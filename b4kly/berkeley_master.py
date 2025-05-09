import socket
import random

HOST = "localhost"
PORT = 5000
NUM_CLIENTS = 3  # Adjust based on the number of clients

def get_random_time():
    """Simulates a local clock with a random time."""
    return random.randint(50, 100)

def main():
    master_time = get_random_time()
    print(f"\nMaster Clock Time: {master_time}")

    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as server_socket:
        server_socket.bind((HOST, PORT))
        server_socket.listen(NUM_CLIENTS)
        print("Master waiting for clients to connect...\n")

        clients = []
        client_times = []

        # Accept connections from clients and receive their times
        for _ in range(NUM_CLIENTS):
            client_socket, addr = server_socket.accept()
            clients.append(client_socket)
            print(f"Client {addr} connected.")

            client_time = int(client_socket.recv(1024).decode())
            print(f"Received time from client {addr}: {client_time}")
            client_times.append(client_time)

        # Calculate average time
        total_time = master_time + sum(client_times)
        avg_time = total_time // (NUM_CLIENTS + 1)

        print(f"\nCalculated average time: {avg_time}")

        # Calculate and send individual adjustments
        for i, client in enumerate(clients):
            adjustment = avg_time - client_times[i]
            client.sendall(str(adjustment).encode())
            client.close()
            print(f"Sent adjustment {adjustment} to client {i+1}")

        # Adjust master's own clock
        master_adjustment = avg_time - master_time
        master_time += master_adjustment
        print(f"Updated Master Clock: {master_time}")

if __name__ == "__main__":
    main()
