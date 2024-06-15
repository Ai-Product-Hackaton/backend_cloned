import torch
import clip
from PIL import Image
import sys

items_dict = {
    "chandelier": "Chandelier",
    "led_panel": "LED panel",
    "night_light": "A night light (which you just plug into the socket and it shines)",
    "desk_lamp": "Desk lamp",
    "outdoor_wall_lamp": "Outdoor Wall Lamp",
    "street_lamp": "Street lamp pole or on a pole",
    "grill_barbecue": "Grill/Barbecue",
    "swimming_pool": "Swimming pool",
    "table": "Table",
    "lounge_chair": "Lounge chair",
    "hanging_chair": "Hanging chair",
    "garden_umbrella": "Garden umbrella",
    "greenhouse": "Greenhouse house"
}

model, preprocess = clip.load("ViT-L/14@336px")
device = 'cpu'

def open_image(image_path):
    image = Image.open(image_path)
    image = image.convert("RGB")
    return image

def predict_label(image_path):
    image = open_image(image_path)
    encoded_image = preprocess(image).unsqueeze(0).to(device)

    encoded_texts = {key: clip.tokenize([value]).to(device) for key, value in items_dict.items()}

    similarities = {}

    with torch.no_grad():
        image_features = model.encode_image(encoded_image)
        for key, text in encoded_texts.items():
            text_features = model.encode_text(text)
            similarity = torch.cosine_similarity(image_features, text_features, dim=-1)
            similarities[key] = similarity.item()

    predicted_key = max(similarities, key=similarities.get)
    return predicted_key

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python script_name.py <path_to_image>")
        sys.exit(1)

    image_path = sys.argv[1]
    predicted_label = predict_label(image_path)
    print(predicted_label)
