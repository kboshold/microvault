#!/usr/bin/env sh
MICROVAULT_INSTALL_LOCATION=$HOME/.local/microvault
MICROVAULT_INSTALL_SOURCE=https://github.com/kpalatzky/microvault/releases/latest/download

mkdir -p $MICROVAULT_INSTALL_LOCATION
wget -P $MICROVAULT_INSTALL_LOCATION $MICROVAULT_INSTALL_SOURCE/microvault
ln -s  $MICROVAULT_INSTALL_LOCATION/microvault $MICROVAULT_INSTALL_LOCATION/miva

PATH_VARIABLE='$PATH'
if [ -f "$HOME/.bashrc" ]; then
cat <<EOT >> "$HOME/.bashrc"
# Add MicroVault to Path
export PATH=$MICROVAULT_INSTALL_LOCATION:$PATH_VARIABLE
EOT
fi

if [ -f "$HOME/.zshrc" ]; then
cat <<EOT >> "$HOME/.zshrc"
# Add MicroVault to Path
export PATH=$MICROVAULT_INSTALL_LOCATION:$PATH_VARIABLE
EOT
fi